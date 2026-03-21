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
    
    private lateinit var summaryCard: androidx.cardview.widget.CardView
    private lateinit var screenTimeChartCard: androidx.cardview.widget.CardView
    private lateinit var appUsageChartCard: androidx.cardview.widget.CardView
    private lateinit var topAppsCard: androidx.cardview.widget.CardView
    private lateinit var topUnlockAppsCard: androidx.cardview.widget.CardView
    private lateinit var topNotificationAppsCard: androidx.cardview.widget.CardView

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
        
        // Trigger automatic silent sync when dashboard is opened
        CoroutineScope(Dispatchers.IO).launch {
            FirebaseSync.syncData(this@DashboardActivity)
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
        summaryCard = findViewById(R.id.summaryCard)
        screenTimeChartCard = findViewById(R.id.screenTimeChartCard)
        appUsageChartCard = findViewById(R.id.appUsageChartCard)
        topAppsCard = findViewById(R.id.topAppsCard)
        topUnlockAppsCard = findViewById(R.id.topUnlockAppsCard)
        topNotificationAppsCard = findViewById(R.id.topNotificationAppsCard)
    }
    
    private fun loadDashboardData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val collector = UsageStatsCollector(this@DashboardActivity)
                
                // Get yesterday's exact period dynamically
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfYesterday = calendar.timeInMillis
                
                calendar.set(Calendar.HOUR_OF_DAY, 23)
                calendar.set(Calendar.MINUTE, 59)
                calendar.set(Calendar.SECOND, 59)
                calendar.set(Calendar.MILLISECOND, 999)
                val endOfYesterday = calendar.timeInMillis
                
                val yesterdayLabel = dateFormat.format(calendar.time)
                
                val yesterdayData = withContext(Dispatchers.IO) {
                    collector.collectUsageData(startOfYesterday, endOfYesterday, yesterdayLabel)
                }
                
                if (yesterdayData != null && yesterdayData.totalScreenTimeMs > 0) {
                    displayData(yesterdayData)
                } else {
                    displayNoData()
                }
                
                // Load true 24-hr daily blocks for chart gracefully avoiding 2-hr fragments
                val last7DaysData = mutableListOf<DailyUsage>()
                for (i in 0..6) {
                    val c = Calendar.getInstance()
                    c.add(Calendar.DAY_OF_YEAR, -i)
                    c.set(Calendar.HOUR_OF_DAY, 0)
                    c.set(Calendar.MINUTE, 0)
                    c.set(Calendar.SECOND, 0)
                    c.set(Calendar.MILLISECOND, 0)
                    val startDay = c.timeInMillis
                    
                    c.set(Calendar.HOUR_OF_DAY, 23)
                    c.set(Calendar.MINUTE, 59)
                    c.set(Calendar.SECOND, 59)
                    c.set(Calendar.MILLISECOND, 999)
                    val endDay = c.timeInMillis
                    
                    val dayLabel = dateFormat.format(c.time)
                    val data = withContext(Dispatchers.IO) {
                        collector.collectUsageData(startDay, endDay, dayLabel)
                    }
                    if (data != null) last7DaysData.add(data)
                }
                
                updateScreenTimeChart(last7DaysData)
                
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@DashboardActivity, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun displayData(usage: DailyUsage) {
        val day = usage.studyDay
        studyDayText.text = if (day > 10) "Study Completed" else "Study Day $day of 10"
        dateText.text = "Data from: ${usage.date}"
        
        val screenTimeMinutes = usage.totalScreenTimeMs / 60000
        val hours = screenTimeMinutes / 60
        val minutes = screenTimeMinutes % 60
        screenTimeText.text = "Total Screen Time: ${hours}h ${minutes}m"
        
        unlocksText.text = "Phone Unlocks: ${usage.totalUnlocks}"
        notificationsText.text = "Notifications: ${usage.totalNotifications}"
        
        // Show all cards
        appUsageChartCard.visibility = android.view.View.VISIBLE
        topAppsCard.visibility = android.view.View.VISIBLE
        topUnlockAppsCard.visibility = android.view.View.VISIBLE
        topNotificationAppsCard.visibility = android.view.View.VISIBLE
        
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
        this.topNotificationAppsText.text = "Most Notifications:\n$topNotifText"
        
        // Update pie chart for app usage
        updateAppUsagePieChart(topApps)
    }
    
    private fun displayNoData() {
        val day = prefs.getStudyDay()
        studyDayText.text = if (day > 10) "Study Completed" else "Study Day $day of 10"
        dateText.text = "No data available yet"
        screenTimeText.text = "Wait for the next data collection."
        unlocksText.text = ""
        notificationsText.text = ""
        topAppsText.text = ""
        topUnlockAppsText.text = ""
        topNotificationAppsText.text = ""
        
        // Hide completely empty cards
        appUsageChartCard.visibility = android.view.View.GONE
        topAppsCard.visibility = android.view.View.GONE
        topUnlockAppsCard.visibility = android.view.View.GONE
        topNotificationAppsCard.visibility = android.view.View.GONE
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
        xAxis.labelRotationAngle = -45f
        
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
}
