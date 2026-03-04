package com.research.usagetracker

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var studyDayText: TextView
    private lateinit var dateText: TextView
    private lateinit var screenTimeText: TextView
    private lateinit var unlocksText: TextView
    private lateinit var notificationsText: TextView
    private lateinit var topAppsText: TextView
    private lateinit var topUnlockAppsText: TextView
    private lateinit var topNotificationAppsText: TextView
    private lateinit var screenTimeChart: BarChart
    private lateinit var appUsageChart: PieChart
    private lateinit var syncButton: Button
    
    private lateinit var database: UsageDatabase
    private lateinit var prefs: AppPreferences
    private val gson = Gson()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        database = UsageDatabase.getDatabase(this)
        prefs = AppPreferences(this)
        
        initViews()
        loadDashboardData()
        
        syncButton.setOnClickListener {
            syncData()
        }
    }
    
    private fun initViews() {
        studyDayText = findViewById(R.id.studyDayText)
        dateText = findViewById(R.id.dateText)
        screenTimeText = findViewById(R.id.screenTimeText)
        unlocksText = findViewById(R.id.unlocksText)
        notificationsText = findViewById(R.id.notificationsText)
        topAppsText = findViewById(R.id.topAppsText)
        topUnlockAppsText = findViewById(R.id.topUnlockAppsText)
        topNotificationAppsText = findViewById(R.id.topNotificationAppsText)
        screenTimeChart = findViewById(R.id.screenTimeChart)
        appUsageChart = findViewById(R.id.appUsageChart)
        syncButton = findViewById(R.id.syncButton)
    }
    
    private fun loadDashboardData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Get yesterday's date
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val yesterday = dateFormat.format(calendar.time)
                
                val yesterdayData = withContext(Dispatchers.IO) {
                    database.usageDao().getUsageByDate(yesterday)
                }
                
                if (yesterdayData != null) {
                    displayData(yesterdayData)
                } else {
                    // No data for yesterday yet
                    displayNoData()
                }
                
                // Load chart data for last 7 days
                loadChartData()
                
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@DashboardActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun displayData(usage: DailyUsage) {
        studyDayText.text = "Study Day ${usage.studyDay} of 10"
        dateText.text = "Data from: ${usage.date}"
        
        val screenTimeMinutes = usage.totalScreenTimeMs / 60000
        val hours = screenTimeMinutes / 60
        val minutes = screenTimeMinutes % 60
        screenTimeText.text = "Total Screen Time: ${hours}h ${minutes}m"
        
        unlocksText.text = "Phone Unlocks: ${usage.totalUnlocks}"
        notificationsText.text = "Notifications: ${usage.totalNotifications}"
        
        // Top apps
        val appUsageList = gson.fromJson(usage.appUsageJson, Array<AppUsage>::class.java).toList()
        val topApps = appUsageList.take(5)
        val topAppsText = topApps.joinToString("\n") { 
            "• ${it.appName}: ${it.usageTimeMinutes} min"
        }
        this.topAppsText.text = "Top Apps:\n$topAppsText"
        
        // Top unlock apps
        val unlockAppsList = gson.fromJson(usage.unlockAppsJson, Array<UnlockAppCount>::class.java).toList()
        val topUnlockApps = unlockAppsList.take(5)
        val topUnlockAppsText = topUnlockApps.joinToString("\n") {
            "• ${it.appName}: ${it.count} unlocks"
        }
        this.topUnlockAppsText.text = "Apps Opened After Unlock:\n$topUnlockAppsText"
        
        // Top notification apps
        val notificationsList = gson.fromJson(usage.notificationsByAppJson, Array<NotificationCount>::class.java).toList()
        val topNotifApps = notificationsList.take(5)
        val topNotifText = topNotifApps.joinToString("\n") {
            "• ${it.appName}: ${it.count} notifications"
        }
        topNotificationAppsText.text = "Most Notifications:\n$topNotifText"
        
        // Update pie chart for app usage
        updateAppUsagePieChart(topApps)
    }
    
    private fun displayNoData() {
        studyDayText.text = "Study Day ${prefs.getStudyDay()} of 10"
        dateText.text = "No data available yet"
        screenTimeText.text = "Check back tomorrow!"
        unlocksText.text = ""
        notificationsText.text = ""
        topAppsText.text = ""
        topUnlockAppsText.text = ""
        topNotificationAppsText.text = ""
    }
    
    private suspend fun loadChartData() = withContext(Dispatchers.IO) {
        val recentData = database.usageDao().getRecentUsage()
        withContext(Dispatchers.Main) {
            updateScreenTimeChart(recentData)
        }
    }
    
    private fun updateScreenTimeChart(data: List<DailyUsage>) {
        if (data.isEmpty()) {
            screenTimeChart.clear()
            return
        }
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()
        
        data.reversed().forEachIndexed { index, usage ->
            val hours = (usage.totalScreenTimeMs / 60000 / 60).toFloat()
            entries.add(BarEntry(index.toFloat(), hours))
            labels.add(usage.date.substring(5)) // MM-DD
        }
        
        val dataSet = BarDataSet(entries, "Screen Time (hours)")
        dataSet.color = ColorTemplate.MATERIAL_COLORS[0]
        dataSet.valueTextSize = 10f
        
        val barData = BarData(dataSet)
        screenTimeChart.data = barData
        
        val xAxis = screenTimeChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        
        screenTimeChart.description.isEnabled = false
        screenTimeChart.animateY(1000)
        screenTimeChart.invalidate()
    }
    
    private fun updateAppUsagePieChart(apps: List<AppUsage>) {
        if (apps.isEmpty()) {
            appUsageChart.clear()
            return
        }
        val entries = mutableListOf<PieEntry>()
        
        apps.forEach { app ->
            entries.add(PieEntry(app.usageTimeMinutes.toFloat(), app.appName))
        }
        
        val dataSet = PieDataSet(entries, "Top Apps")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE
        
        val pieData = PieData(dataSet)
        appUsageChart.data = pieData
        appUsageChart.description.isEnabled = false
        appUsageChart.centerText = "App Usage"
        appUsageChart.animateY(1000)
        appUsageChart.invalidate()
    }
    
    private fun syncData() {
        CoroutineScope(Dispatchers.Main).launch {
            syncButton.isEnabled = false
            syncButton.text = "Syncing..."
            
            val result = withContext(Dispatchers.IO) {
                FirebaseSync.syncData(this@DashboardActivity)
            }
            
            syncButton.isEnabled = true
            syncButton.text = "Sync to Firebase"
            
            val toastMsg = if (result.first) "Success: ${result.second}" else "Failed: ${result.second}"
            Toast.makeText(this@DashboardActivity, toastMsg, Toast.LENGTH_LONG).show()
        }
    }
}
