package com.research.usagetracker

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object FirebaseSync {
    
    // IMPORTANT: Replace this with your Firebase Cloud Function URL after deployment
    private const val FIREBASE_FUNCTION_URL = "https://us-central1-ecf-survey-app.cloudfunctions.net/syncUsageData"
    
    private val gson = Gson()
    
    suspend fun syncData(context: Context): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        try {
            val prefs = AppPreferences(context)
            val participantToken = prefs.getParticipantToken()
            
            if (participantToken.isEmpty()) {
                Log.e("FirebaseSync", "No participant token found")
                return@withContext Pair(false, "Not registered. No token.")
            }
            
            val database = UsageDatabase.getDatabase(context)
            val unsyncedData = database.usageDao().getUnsyncedUsage()
            
            if (unsyncedData.isEmpty()) {
                Log.d("FirebaseSync", "No data to sync")
                return@withContext Pair(true, "No new data to sync.")
            }
            
            // Prepare data payload
            val dataArray = JSONArray()
            
            for (usage in unsyncedData) {
                val appUsageList = gson.fromJson(usage.appUsageJson, Array<AppUsage>::class.java).toList()
                val unlockAppsList = gson.fromJson(usage.unlockAppsJson, Array<UnlockAppCount>::class.java).toList()
                val notificationsList = gson.fromJson(usage.notificationsByAppJson, Array<NotificationCount>::class.java).toList()
                
                val dataItem = JSONObject().apply {
                    put("date", usage.date)
                    put("userName", usage.userName)
                    put("anonymousId", usage.anonymousId)
                    put("studyDay", usage.studyDay)
                    put("totalScreenTimeMin", usage.totalScreenTimeMs / 60000)
                    put("topApps", formatTopApps(appUsageList.take(5)))
                    put("totalUnlocks", usage.totalUnlocks)
                    put("topUnlockApps", formatTopUnlockApps(unlockAppsList.take(5)))
                    put("totalNotifications", usage.totalNotifications)
                    put("topNotificationApps", formatTopNotificationApps(notificationsList.take(5)))
                }
                
                dataArray.put(dataItem)
            }
            
            // Create request payload
            val payload = JSONObject().apply {
                put("participantToken", participantToken)
                put("data", dataArray)
            }
            
            // Send POST request to Firebase Function
            val url = URL(FIREBASE_FUNCTION_URL)
            val connection = url.openConnection() as HttpURLConnection
            
            try {
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                connection.connectTimeout = 30000
                connection.readTimeout = 30000
                
                // Write payload
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(payload.toString())
                    writer.flush()
                }
                
                // Check response
                val responseCode = connection.responseCode
                
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)
                    
                    if (jsonResponse.getBoolean("success")) {
                        val rowsAdded = jsonResponse.getInt("rowsAdded")
                        Log.d("FirebaseSync", "Successfully synced $rowsAdded rows")
                        
                        // Mark as synced
                        for (usage in unsyncedData) {
                            database.usageDao().markAsSynced(usage.id)
                        }
                        
                        prefs.setLastSyncTime(System.currentTimeMillis())
                        return@withContext Pair(true, "Successfully synced $rowsAdded rows")
                    } else {
                        val errorStr = jsonResponse.optString("error")
                        Log.e("FirebaseSync", "Sync failed: $errorStr")
                        return@withContext Pair(false, "Sync failed: $errorStr")
                    }
                } else {
                    val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
                    Log.e("FirebaseSync", "HTTP error $responseCode: $errorResponse")
                    return@withContext Pair(false, "Server Error $responseCode")
                }
            } finally {
                connection.disconnect()
            }
            
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error syncing data", e)
            return@withContext Pair(false, "Network error: ${e.message}")
        }
    }
    
    suspend fun registerParticipant(context: Context, userName: String, anonymousId: String): String? = withContext(Dispatchers.IO) {
        try {
            val registerUrl = FIREBASE_FUNCTION_URL.replace("/syncUsageData", "/registerParticipant")
            val url = URL(registerUrl)
            val connection = url.openConnection() as HttpURLConnection
            
            try {
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                connection.connectTimeout = 30000
                connection.readTimeout = 30000
                
                val payload = JSONObject().apply {
                    put("userName", userName)
                    put("anonymousId", anonymousId)
                }
                
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(payload.toString())
                    writer.flush()
                }
                
                val responseCode = connection.responseCode
                
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)
                    
                    if (jsonResponse.getBoolean("success")) {
                        val token = jsonResponse.getString("participantToken")
                        Log.d("FirebaseSync", "Participant registered successfully")
                        return@withContext token
                    }
                }
                
                Log.e("FirebaseSync", "Registration failed with code: $responseCode")
                return@withContext null
                
            } finally {
                connection.disconnect()
            }
            
        } catch (e: Exception) {
            Log.e("FirebaseSync", "Error registering participant", e)
            return@withContext null
        }
    }
    
    private fun formatTopApps(apps: List<AppUsage>): String {
        return apps.joinToString("; ") { "${it.appName}: ${it.usageTimeMinutes}min" }
    }
    
    private fun formatTopUnlockApps(unlockApps: List<UnlockAppCount>): String {
        return unlockApps.joinToString("; ") { "${it.appName}: ${it.count}x" }
    }
    
    private fun formatTopNotificationApps(notifications: List<NotificationCount>): String {
        return notifications.joinToString("; ") { "${it.appName}: ${it.count}" }
    }
}
