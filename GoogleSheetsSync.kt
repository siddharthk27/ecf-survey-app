package com.research.usagetracker

import android.content.Context
import android.util.Log
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.AppendValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GoogleSheetsSync {
    
    private const val APPLICATION_NAME = "Usage Tracker Research"
    private val SCOPES = listOf(SheetsScopes.SPREADSHEETS)
    private val gson = Gson()
    
    suspend fun syncData(context: Context) = withContext(Dispatchers.IO) {
        try {
            val prefs = AppPreferences(context)
            val sheetId = prefs.getSheetId()
            
            if (sheetId.isEmpty()) {
                Log.e("GoogleSheetsSync", "No sheet ID configured")
                return@withContext
            }
            
            val database = UsageDatabase.getDatabase(context)
            val unsyncedData = database.usageDao().getUnsyncedUsage()
            
            if (unsyncedData.isEmpty()) {
                Log.d("GoogleSheetsSync", "No data to sync")
                return@withContext
            }
            
            // Get Google Sheets service
            val credential = getCredential(context)
            val sheetsService = getSheetsService(credential)
            
            // Prepare data rows
            val values = mutableListOf<List<Any>>()
            
            for (usage in unsyncedData) {
                val appUsageList = gson.fromJson(usage.appUsageJson, Array<AppUsage>::class.java).toList()
                val unlockAppsList = gson.fromJson(usage.unlockAppsJson, Array<UnlockAppCount>::class.java).toList()
                val notificationsList = gson.fromJson(usage.notificationsByAppJson, Array<NotificationCount>::class.java).toList()
                
                // Format: Date, UserName, AnonymousID, StudyDay, TotalScreenTime(min), TopApps, TotalUnlocks, TopUnlockApps, TotalNotifications, TopNotificationApps
                val row = listOf(
                    usage.date,
                    usage.userName,
                    usage.anonymousId,
                    usage.studyDay.toString(),
                    (usage.totalScreenTimeMs / 60000).toString(), // Convert to minutes
                    formatTopApps(appUsageList.take(5)),
                    usage.totalUnlocks.toString(),
                    formatTopUnlockApps(unlockAppsList.take(5)),
                    usage.totalNotifications.toString(),
                    formatTopNotificationApps(notificationsList.take(5))
                )
                values.add(row)
            }
            
            // Append to Google Sheets
            val range = "Sheet1!A:J" // Updated to include unlock apps column
            val body = ValueRange().setValues(values)
            
            val result: AppendValuesResponse = sheetsService.spreadsheets().values()
                .append(sheetId, range, body)
                .setValueInputOption("RAW")
                .execute()
            
            Log.d("GoogleSheetsSync", "Synced ${result.updates.updatedRows} rows")
            
            // Mark as synced
            for (usage in unsyncedData) {
                database.usageDao().markAsSynced(usage.id)
            }
            
            prefs.setLastSyncTime(System.currentTimeMillis())
            
        } catch (e: Exception) {
            Log.e("GoogleSheetsSync", "Error syncing data", e)
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
    
    private fun getCredential(context: Context): GoogleAccountCredential {
        return GoogleAccountCredential.usingOAuth2(
            context,
            SCOPES
        ).setBackOff(null)
    }
    
    private fun getSheetsService(credential: GoogleAccountCredential): Sheets {
        val transport = GoogleNetHttpTransport.newTrustedTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        
        return Sheets.Builder(transport, jsonFactory, credential)
            .setApplicationName(APPLICATION_NAME)
            .build()
    }
}
