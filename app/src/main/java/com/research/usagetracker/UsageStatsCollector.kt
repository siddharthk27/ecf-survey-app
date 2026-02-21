package com.research.usagetracker

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class UsageStatsCollector(private val context: Context) {
    
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private val packageManager = context.packageManager
    private val gson = Gson()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    suspend fun collectUsageData(startTime: Long, endTime: Long, label: String): DailyUsage? = withContext(Dispatchers.IO) {
        try {
            val prefs = AppPreferences(context)
            val database = UsageDatabase.getDatabase(context)
            
            // Get usage stats for the day
            val usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
            )
            
            // Calculate total screen time and per-app usage
            var totalScreenTime = 0L
            val appUsageList = mutableListOf<AppUsage>()
            
            usageStatsList
                .filter { it.totalTimeInForeground > 0 }
                .sortedByDescending { it.totalTimeInForeground }
                .forEach { stats ->
                    val appName = getAppName(stats.packageName)
                    totalScreenTime += stats.totalTimeInForeground
                    
                    appUsageList.add(
                        AppUsage(
                            appName = appName,
                            packageName = stats.packageName,
                            usageTimeMs = stats.totalTimeInForeground,
                            usageTimeMinutes = (stats.totalTimeInForeground / 60000).toInt()
                        )
                    )
                }
            
            // Get unlock events for the period
            val unlockEvents = database.usageDao().getUnlocksBetween(startTime, endTime)
            val unlockCount = unlockEvents.size
            
            // Aggregate unlock apps (which apps were opened after unlocking)
            val unlockApps = unlockEvents
                .filter { it.appPackage != null }
                .groupBy { it.appPackage!! }
                .map { (packageName, events) ->
                    val appName = getAppName(packageName)
                    UnlockAppCount(appName, packageName, events.size)
                }
                .sortedByDescending { it.count }
            
            // Get notifications for the period
            val notifications = database.usageDao().getNotificationsBetween(startTime, endTime)
            val notificationCount = notifications.size
            
            // Group notifications by app
            val notificationsByApp = notifications
                .groupBy { it.appName }
                .map { NotificationCount(it.key, it.value.size) }
                .sortedByDescending { it.count }
            
            // Create daily usage record
            DailyUsage(
                date = label,
                userName = prefs.getUserName(),
                anonymousId = prefs.getAnonymousId(),
                studyDay = prefs.getStudyDay(),
                totalScreenTimeMs = totalScreenTime,
                appUsageJson = gson.toJson(appUsageList),
                totalUnlocks = unlockCount,
                unlockAppsJson = gson.toJson(unlockApps),
                totalNotifications = notificationCount,
                notificationsByAppJson = gson.toJson(notificationsByApp)
            )
        } catch (e: Exception) {
            Log.e("UsageStatsCollector", "Error collecting usage data", e)
            null
        }
    }
    
    private fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }
    
    private fun getAppName(packageName: String): String {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: Exception) {
            packageName
        }
    }
}
