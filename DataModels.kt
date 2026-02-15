package com.research.usagetracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_usage")
data class DailyUsage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // YYYY-MM-DD format
    val userName: String,
    val anonymousId: String,
    val studyDay: Int,
    val totalScreenTimeMs: Long,
    val appUsageJson: String, // JSON array of app usage
    val totalUnlocks: Int,
    val unlockAppsJson: String, // JSON object of app->count for unlock apps
    val totalNotifications: Int,
    val notificationsByAppJson: String, // JSON object of app->count
    val synced: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "unlock_events")
data class UnlockEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val date: String, // YYYY-MM-DD format
    val appPackage: String?, // App that was opened after unlock
    val synced: Boolean = false
)

@Entity(tableName = "notification_events")
data class NotificationEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val date: String, // YYYY-MM-DD format
    val appPackage: String,
    val appName: String,
    val synced: Boolean = false
)

// Data classes for JSON serialization
data class AppUsage(
    val appName: String,
    val packageName: String,
    val usageTimeMs: Long,
    val usageTimeMinutes: Int
)

data class UnlockAppCount(
    val appName: String,
    val packageName: String,
    val count: Int
)

data class NotificationCount(
    val appName: String,
    val count: Int
)
