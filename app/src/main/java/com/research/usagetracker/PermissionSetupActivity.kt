package com.research.usagetracker

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

class PermissionSetupActivity : AppCompatActivity() {
    
    private lateinit var usageAccessButton: Button
    private lateinit var notificationAccessButton: Button
    private lateinit var continueButton: Button
    private lateinit var statusText: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_setup)
        
        usageAccessButton = findViewById(R.id.usageAccessButton)
        notificationAccessButton = findViewById(R.id.notificationAccessButton)
        continueButton = findViewById(R.id.continueButton)
        statusText = findViewById(R.id.statusText)
        
        usageAccessButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        
        notificationAccessButton.setOnClickListener {
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
        }
        
        continueButton.setOnClickListener {
            if (hasUsageStatsPermission() && hasNotificationListenerPermission()) {
                // Start services and navigate to dashboard
                startTrackingServices()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                updateStatus()
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        updateStatus()
    }
    
    private fun updateStatus() {
        val hasUsage = hasUsageStatsPermission()
        val hasNotification = hasNotificationListenerPermission()
        
        usageAccessButton.isEnabled = !hasUsage
        notificationAccessButton.isEnabled = !hasNotification
        
        val status = StringBuilder("Permission Status:\n")
        status.append("✓ Usage Access: ${if (hasUsage) "Granted" else "Not Granted"}\n")
        status.append("✓ Notification Access: ${if (hasNotification) "Granted" else "Not Granted"}")
        
        statusText.text = status.toString()
        
        continueButton.isEnabled = hasUsage && hasNotification
    }
    
    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }
    
    private fun hasNotificationListenerPermission(): Boolean {
        val enabledListeners = NotificationManagerCompat.getEnabledListenerPackages(this)
        return enabledListeners.contains(packageName)
    }
    
    private fun startTrackingServices() {
        // Start the usage tracking service
        startService(Intent(this, UsageTrackingService::class.java))
        
        // Schedule midnight data collection
        MidnightScheduler.scheduleMidnightTask(this)
        
        // Schedule daily reminder notifications
        DailyReminderScheduler.scheduleDailyReminder(this)
    }
}
