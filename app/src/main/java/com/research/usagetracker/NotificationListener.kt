package com.research.usagetracker

import android.content.pm.PackageManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationListener : NotificationListenerService() {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var database: UsageDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    override fun onCreate() {
        super.onCreate()
        database = UsageDatabase.getDatabase(this)
        Log.d("NotificationListener", "Notification listener service created")
    }
    
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        sbn?.let {
            // Filter out system notifications and our own app
            if (it.packageName == packageName || isSystemPackage(it.packageName)) {
                return
            }
            
            val appName = getAppName(it.packageName)
            val currentDate = dateFormat.format(Date())
            
            scope.launch {
                try {
                    val event = NotificationEvent(
                        timestamp = System.currentTimeMillis(),
                        date = currentDate,
                        appPackage = it.packageName,
                        appName = appName
                    )
                    database.usageDao().insertNotificationEvent(event)
                    Log.d("NotificationListener", "Logged notification from: $appName")
                } catch (e: Exception) {
                    Log.e("NotificationListener", "Error logging notification", e)
                }
            }
        }
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val pm = packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }
    
    private fun isSystemPackage(packageName: String): Boolean {
        val systemPackages = listOf(
            "android",
            "com.android.systemui",
            "com.android.providers",
            "com.google.android.gms"
        )
        return systemPackages.any { packageName.startsWith(it) }
    }
}
