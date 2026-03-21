package com.research.usagetracker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020 H\u0002J\b\u0010$\u001a\u00020 H\u0002J\b\u0010%\u001a\u00020 H\u0002J\u0012\u0010&\u001a\u00020 2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0014J\u0016\u0010)\u001a\u00020 2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0002J\u0016\u0010-\u001a\u00020 2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\"0+H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/research/usagetracker/DashboardActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "appUsageChart", "Lcom/github/mikephil/charting/charts/PieChart;", "appUsageChartCard", "Landroidx/cardview/widget/CardView;", "database", "Lcom/research/usagetracker/UsageDatabase;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dateText", "Landroid/widget/TextView;", "gson", "Lcom/google/gson/Gson;", "notificationsText", "prefs", "Lcom/research/usagetracker/AppPreferences;", "screenTimeChart", "Lcom/github/mikephil/charting/charts/BarChart;", "screenTimeChartCard", "screenTimeText", "studyDayText", "summaryCard", "topAppsCard", "topAppsText", "topNotificationAppsCard", "topNotificationAppsText", "topUnlockAppsCard", "topUnlockAppsText", "unlocksText", "displayData", "", "usage", "Lcom/research/usagetracker/DailyUsage;", "displayNoData", "initViews", "loadDashboardData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "updateAppUsagePieChart", "apps", "", "Lcom/research/usagetracker/AppUsage;", "updateScreenTimeChart", "data", "app_debug"})
public final class DashboardActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.TextView studyDayText;
    private android.widget.TextView dateText;
    private android.widget.TextView screenTimeText;
    private android.widget.TextView unlocksText;
    private android.widget.TextView notificationsText;
    private android.widget.TextView topAppsText;
    private android.widget.TextView topUnlockAppsText;
    private android.widget.TextView topNotificationAppsText;
    private com.github.mikephil.charting.charts.BarChart screenTimeChart;
    private com.github.mikephil.charting.charts.PieChart appUsageChart;
    private androidx.cardview.widget.CardView summaryCard;
    private androidx.cardview.widget.CardView screenTimeChartCard;
    private androidx.cardview.widget.CardView appUsageChartCard;
    private androidx.cardview.widget.CardView topAppsCard;
    private androidx.cardview.widget.CardView topUnlockAppsCard;
    private androidx.cardview.widget.CardView topNotificationAppsCard;
    private com.research.usagetracker.UsageDatabase database;
    private com.research.usagetracker.AppPreferences prefs;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public DashboardActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initViews() {
    }
    
    private final void loadDashboardData() {
    }
    
    private final void displayData(com.research.usagetracker.DailyUsage usage) {
    }
    
    private final void displayNoData() {
    }
    
    private final void updateScreenTimeChart(java.util.List<com.research.usagetracker.DailyUsage> data) {
    }
    
    private final void updateAppUsagePieChart(java.util.List<com.research.usagetracker.AppUsage> apps) {
    }
}