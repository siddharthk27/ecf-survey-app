package com.research.usagetracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                Log.d("BootReceiver", "Device booted, restarting services")
                
                val prefs = AppPreferences(it)
                if (prefs.isRegistered()) {
                    // Restart the usage tracking service
                    it.startService(Intent(it, UsageTrackingService::class.java))
                    
                    // Reschedule alarms
                    MidnightScheduler.scheduleMidnightTask(it)
                    DailyReminderScheduler.scheduleDailyReminder(it)
                }
            }
        }
    }
}
