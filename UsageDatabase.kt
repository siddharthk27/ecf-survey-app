package com.research.usagetracker

import android.content.Context
import androidx.room.*

@Dao
interface UsageDao {
    @Insert
    suspend fun insertDailyUsage(usage: DailyUsage): Long
    
    @Query("SELECT * FROM daily_usage ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentUsage(): List<DailyUsage>
    
    @Query("SELECT * FROM daily_usage WHERE date = :date LIMIT 1")
    suspend fun getUsageByDate(date: String): DailyUsage?
    
    @Query("SELECT * FROM daily_usage WHERE synced = 0")
    suspend fun getUnsyncedUsage(): List<DailyUsage>
    
    @Query("UPDATE daily_usage SET synced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
    
    @Insert
    suspend fun insertUnlockEvent(event: UnlockEvent): Long
    
    @Query("SELECT * FROM unlock_events WHERE date = :date")
    suspend fun getUnlocksByDate(date: String): List<UnlockEvent>
    
    @Query("SELECT COUNT(*) FROM unlock_events WHERE date = :date")
    suspend fun getUnlockCountByDate(date: String): Int
    
    @Insert
    suspend fun insertNotificationEvent(event: NotificationEvent): Long
    
    @Query("SELECT * FROM notification_events WHERE date = :date")
    suspend fun getNotificationsByDate(date: String): List<NotificationEvent>
    
    @Query("SELECT COUNT(*) FROM notification_events WHERE date = :date")
    suspend fun getNotificationCountByDate(date: String): Int
    
    @Query("DELETE FROM unlock_events WHERE date < :date")
    suspend fun deleteOldUnlocks(date: String)
    
    @Query("DELETE FROM notification_events WHERE date < :date")
    suspend fun deleteOldNotifications(date: String)
}

@Database(
    entities = [DailyUsage::class, UnlockEvent::class, NotificationEvent::class],
    version = 2,
    exportSchema = false
)
abstract class UsageDatabase : RoomDatabase() {
    abstract fun usageDao(): UsageDao
    
    companion object {
        @Volatile
        private var INSTANCE: UsageDatabase? = null
        
        fun getDatabase(context: Context): UsageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsageDatabase::class.java,
                    "usage_database"
                )
                .fallbackToDestructiveMigration() // For research app, can recreate DB on schema change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
