package com.research.usagetracker

import android.app.*
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class UsageTrackingService : Service() {
    
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var database: UsageDatabase
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val handler = Handler(Looper.getMainLooper())
    
    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_USER_PRESENT -> {
                    // Screen unlocked - wait a moment then detect the app
                    logUnlockEvent()
                }
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        database = UsageDatabase.getDatabase(this)
        
        // Register screen unlock receiver
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_USER_PRESENT)
        }
        registerReceiver(screenReceiver, filter)
        
        // Start as foreground service
        startForegroundService()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(screenReceiver)
        } catch (e: Exception) {
            // Receiver might not be registered
        }
    }
    
    private fun startForegroundService() {
        val channelId = createNotificationChannel()
        
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Usage Tracker Running")
            .setContentText("Tracking your phone usage for research")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        
        startForeground(1, notification)
    }
    
    private fun createNotificationChannel(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "usage_tracker_service"
            val channelName = "Usage Tracking Service"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
            return channelId
        }
        return ""
    }
    
    private fun logUnlockEvent() {
        scope.launch {
            try {
                // Wait a short moment (1 second) for the user to open an app
                delay(1000)
                
                val currentDate = dateFormat.format(Date())
                val foregroundApp = getForegroundApp()
                
                val event = UnlockEvent(
                    timestamp = System.currentTimeMillis(),
                    date = currentDate,
                    appPackage = foregroundApp
                )
                database.usageDao().insertUnlockEvent(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private fun getForegroundApp(): String? {
        return try {
            val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val currentTime = System.currentTimeMillis()
            
            // Query events from the last 2 seconds
            val events = usageStatsManager.queryEvents(currentTime - 2000, currentTime)
            var foregroundApp: String? = null
            
            val event = UsageEvents.Event()
            while (events.hasNextEvent()) {
                events.getNextEvent(event)
                
                // Look for the most recent MOVE_TO_FOREGROUND event
                if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    foregroundApp = event.packageName
                }
            }
            
            // Filter out system UI and launcher
            if (foregroundApp != null && !isSystemApp(foregroundApp)) {
                foregroundApp
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun isSystemApp(packageName: String): Boolean {
        val systemPackages = listOf(
            "com.android.systemui",
            "com.google.android.apps.nexuslauncher",
            "com.android.launcher",
            "com.research.usagetracker" // Exclude our own app
        )
        return systemPackages.any { packageName.contains(it, ignoreCase = true) }
    }
}
