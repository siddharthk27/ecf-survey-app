package com.research.usagetracker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002J\u0016\u0010\u000b\u001a\u00020\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\tH\u0002J\u0016\u0010\u000e\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\tH\u0002J+\u0010\u0011\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0019\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u0013H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001a"}, d2 = {"Lcom/research/usagetracker/FirebaseSync;", "", "()V", "FIREBASE_FUNCTION_URL", "", "gson", "Lcom/google/gson/Gson;", "formatTopApps", "apps", "", "Lcom/research/usagetracker/AppUsage;", "formatTopNotificationApps", "notifications", "Lcom/research/usagetracker/NotificationCount;", "formatTopUnlockApps", "unlockApps", "Lcom/research/usagetracker/UnlockAppCount;", "registerParticipant", "context", "Landroid/content/Context;", "userName", "anonymousId", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncData", "", "(Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FirebaseSync {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String FIREBASE_FUNCTION_URL = "https://us-central1-ecf-survey-app.cloudfunctions.net/syncUsageData";
    @org.jetbrains.annotations.NotNull
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    public static final com.research.usagetracker.FirebaseSync INSTANCE = null;
    
    private FirebaseSync() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object syncData(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object registerParticipant(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String userName, @org.jetbrains.annotations.NotNull
    java.lang.String anonymousId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String formatTopApps(java.util.List<com.research.usagetracker.AppUsage> apps) {
        return null;
    }
    
    private final java.lang.String formatTopUnlockApps(java.util.List<com.research.usagetracker.UnlockAppCount> unlockApps) {
        return null;
    }
    
    private final java.lang.String formatTopNotificationApps(java.util.List<com.research.usagetracker.NotificationCount> notifications) {
        return null;
    }
}