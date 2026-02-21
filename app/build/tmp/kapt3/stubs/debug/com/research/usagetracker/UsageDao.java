package com.research.usagetracker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\'\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\'\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J\u001f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00130\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u001b\u0010\u001a\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u0013H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ\u0019\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u0019\u0010!\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0017H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"J\u0019\u0010#\u001a\u00020\u00032\u0006\u0010$\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010%\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006&"}, d2 = {"Lcom/research/usagetracker/UsageDao;", "", "deleteOldNotifications", "", "date", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldUnlocks", "getNotificationCountByDate", "", "getNotificationsBetween", "", "Lcom/research/usagetracker/NotificationEvent;", "startTime", "", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNotificationsByDate", "getRecentUsage", "Lcom/research/usagetracker/DailyUsage;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUnlockCountByDate", "getUnlocksBetween", "Lcom/research/usagetracker/UnlockEvent;", "getUnlocksByDate", "getUnsyncedUsage", "getUsageByDate", "insertDailyUsage", "usage", "(Lcom/research/usagetracker/DailyUsage;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertNotificationEvent", "event", "(Lcom/research/usagetracker/NotificationEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertUnlockEvent", "(Lcom/research/usagetracker/UnlockEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsSynced", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface UsageDao {
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertDailyUsage(@org.jetbrains.annotations.NotNull
    com.research.usagetracker.DailyUsage usage, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM daily_usage ORDER BY timestamp DESC LIMIT 10")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecentUsage(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.DailyUsage>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM daily_usage WHERE date = :date LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUsageByDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.research.usagetracker.DailyUsage> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM daily_usage WHERE synced = 0")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnsyncedUsage(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.DailyUsage>> $completion);
    
    @androidx.room.Query(value = "UPDATE daily_usage SET synced = 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsSynced(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertUnlockEvent(@org.jetbrains.annotations.NotNull
    com.research.usagetracker.UnlockEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM unlock_events WHERE date = :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnlocksByDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.UnlockEvent>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM unlock_events WHERE date = :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnlockCountByDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertNotificationEvent(@org.jetbrains.annotations.NotNull
    com.research.usagetracker.NotificationEvent event, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM notification_events WHERE date = :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNotificationsByDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.NotificationEvent>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM notification_events WHERE date = :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNotificationCountByDate(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "DELETE FROM unlock_events WHERE date < :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldUnlocks(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM notification_events WHERE date < :date")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldNotifications(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM unlock_events WHERE timestamp BETWEEN :startTime AND :endTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnlocksBetween(long startTime, long endTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.UnlockEvent>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM notification_events WHERE timestamp BETWEEN :startTime AND :endTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNotificationsBetween(long startTime, long endTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.research.usagetracker.NotificationEvent>> $completion);
}