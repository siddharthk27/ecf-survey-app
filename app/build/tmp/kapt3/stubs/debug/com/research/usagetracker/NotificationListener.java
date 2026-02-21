package com.research.usagetracker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/research/usagetracker/NotificationListener;", "Landroid/service/notification/NotificationListenerService;", "()V", "database", "Lcom/research/usagetracker/UsageDatabase;", "dateFormat", "Ljava/text/SimpleDateFormat;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "getAppName", "", "packageName", "isSystemPackage", "", "onCreate", "", "onNotificationPosted", "sbn", "Landroid/service/notification/StatusBarNotification;", "app_debug"})
public final class NotificationListener extends android.service.notification.NotificationListenerService {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private com.research.usagetracker.UsageDatabase database;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public NotificationListener() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public void onNotificationPosted(@org.jetbrains.annotations.Nullable
    android.service.notification.StatusBarNotification sbn) {
    }
    
    private final java.lang.String getAppName(java.lang.String packageName) {
        return null;
    }
    
    private final boolean isSystemPackage(java.lang.String packageName) {
        return false;
    }
}