package com.research.usagetracker;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\bJ\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u000f\u001a\u00020\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\nJ\u001e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/research/usagetracker/AppPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "getAnonymousId", "", "getLastSyncTime", "", "getParticipantToken", "getStudyDay", "", "getStudyStartDate", "getUserName", "isRegistered", "", "setLastSyncTime", "", "time", "setUserInfo", "name", "anonymousId", "participantToken", "Companion", "app_debug"})
public final class AppPreferences {
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "UsageTrackerPrefs";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_NAME = "user_name";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_ANONYMOUS_ID = "anonymous_id";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_REGISTERED = "is_registered";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_PARTICIPANT_TOKEN = "participant_token";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LAST_SYNC = "last_sync";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_STUDY_START_DATE = "study_start_date";
    @org.jetbrains.annotations.NotNull
    public static final com.research.usagetracker.AppPreferences.Companion Companion = null;
    
    public AppPreferences(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final void setUserInfo(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String anonymousId, @org.jetbrains.annotations.NotNull
    java.lang.String participantToken) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAnonymousId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getParticipantToken() {
        return null;
    }
    
    public final boolean isRegistered() {
        return false;
    }
    
    public final void setLastSyncTime(long time) {
    }
    
    public final long getLastSyncTime() {
        return 0L;
    }
    
    public final long getStudyStartDate() {
        return 0L;
    }
    
    public final int getStudyDay() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/research/usagetracker/AppPreferences$Companion;", "", "()V", "KEY_ANONYMOUS_ID", "", "KEY_LAST_SYNC", "KEY_PARTICIPANT_TOKEN", "KEY_REGISTERED", "KEY_STUDY_START_DATE", "KEY_USER_NAME", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}