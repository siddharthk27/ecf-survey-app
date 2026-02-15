package com.research.usagetracker

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    companion object {
        private const val PREFS_NAME = "UsageTrackerPrefs"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_ANONYMOUS_ID = "anonymous_id"
        private const val KEY_REGISTERED = "is_registered"
        private const val KEY_SHEET_ID = "sheet_id"
        private const val KEY_LAST_SYNC = "last_sync"
        private const val KEY_STUDY_START_DATE = "study_start_date"
    }
    
    fun setUserInfo(name: String, anonymousId: String) {
        prefs.edit().apply {
            putString(KEY_USER_NAME, name)
            putString(KEY_ANONYMOUS_ID, anonymousId)
            putBoolean(KEY_REGISTERED, true)
            putLong(KEY_STUDY_START_DATE, System.currentTimeMillis())
            apply()
        }
    }
    
    fun getUserName(): String = prefs.getString(KEY_USER_NAME, "") ?: ""
    
    fun getAnonymousId(): String = prefs.getString(KEY_ANONYMOUS_ID, "") ?: ""
    
    fun isRegistered(): Boolean = prefs.getBoolean(KEY_REGISTERED, false)
    
    fun setSheetId(sheetId: String) {
        prefs.edit().putString(KEY_SHEET_ID, sheetId).apply()
    }
    
    fun getSheetId(): String = prefs.getString(KEY_SHEET_ID, "") ?: ""
    
    fun setLastSyncTime(time: Long) {
        prefs.edit().putLong(KEY_LAST_SYNC, time).apply()
    }
    
    fun getLastSyncTime(): Long = prefs.getLong(KEY_LAST_SYNC, 0)
    
    fun getStudyStartDate(): Long = prefs.getLong(KEY_STUDY_START_DATE, System.currentTimeMillis())
    
    fun getStudyDay(): Int {
        val startDate = getStudyStartDate()
        val currentTime = System.currentTimeMillis()
        val daysSinceStart = ((currentTime - startDate) / (1000 * 60 * 60 * 24)).toInt()
        return daysSinceStart + 1 // Day 1, 2, 3... up to 10
    }
}
