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
            
            // Collect yesterday's data
            CoroutineScope(Dispatchers.IO).launch {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val yesterday = android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString()
                
                val collector = UsageStatsCollector(it)
                val dailyUsage = collector.collectDailyUsageData(yesterday)
                
                dailyUsage?.let { usage ->
                    val database = UsageDatabase.getDatabase(it)
                    database.usageDao().insertDailyUsage(usage)
                    Log.d("MidnightAlarm", "Saved daily usage for $yesterday")
                    
                    // Trigger sync to Google Sheets
                    GoogleSheetsSync.syncData(it)
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
        
        // Calculate next midnight
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }
        
        val nextMidnight = calendar.timeInMillis
        
        // Set exact alarm for midnight
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextMidnight,
            pendingIntent
        )
        
        Log.d("MidnightScheduler", "Scheduled next midnight task for: ${Date(nextMidnight)}")
    }
}
