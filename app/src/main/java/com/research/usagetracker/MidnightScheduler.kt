package com.research.usagetracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MidnightAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            Log.d("MidnightAlarm", "Midnight alarm triggered")
            
            // Collect last 2 hours' data
            CoroutineScope(Dispatchers.IO).launch {
                val endTime = System.currentTimeMillis()
                val startTime = endTime - (2 * 60 * 60 * 1000)
                val labelFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                val label = labelFormat.format(java.util.Date(endTime))
                
                val collector = UsageStatsCollector(it)
                val dailyUsage = collector.collectUsageData(startTime, endTime, label)
                
                dailyUsage?.let { usage ->
                    val database = UsageDatabase.getDatabase(it)
                    database.usageDao().insertDailyUsage(usage)
                    Log.d("MidnightAlarm", "Saved daily usage for $label")
                    
                    // Trigger sync to Firebase
                    FirebaseSync.syncData(it)
                }
            }
            
            // Reschedule for next midnight
            MidnightScheduler.scheduleMidnightTask(it)
        }
    }
}

object MidnightScheduler {
    fun scheduleMidnightTask(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MidnightAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Calculate next 2 hours
        val nextTime = System.currentTimeMillis() + (2 * 60 * 60 * 1000)
        
        // Set exact alarm for midnight
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextTime,
            pendingIntent
        )
        
        Log.d("MidnightScheduler", "Scheduled next 2-hour task for: ${Date(nextTime)}")
    }
}
