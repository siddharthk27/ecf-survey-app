package com.research.usagetracker;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UsageDatabase_Impl extends UsageDatabase {
  private volatile UsageDao _usageDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_usage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` TEXT NOT NULL, `userName` TEXT NOT NULL, `anonymousId` TEXT NOT NULL, `studyDay` INTEGER NOT NULL, `totalScreenTimeMs` INTEGER NOT NULL, `appUsageJson` TEXT NOT NULL, `totalUnlocks` INTEGER NOT NULL, `unlockAppsJson` TEXT NOT NULL, `totalNotifications` INTEGER NOT NULL, `notificationsByAppJson` TEXT NOT NULL, `synced` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `unlock_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `date` TEXT NOT NULL, `appPackage` TEXT, `synced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notification_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `date` TEXT NOT NULL, `appPackage` TEXT NOT NULL, `appName` TEXT NOT NULL, `synced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b6bfb0f61bf671d5b01c78b67332e7bc')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `daily_usage`");
        db.execSQL("DROP TABLE IF EXISTS `unlock_events`");
        db.execSQL("DROP TABLE IF EXISTS `notification_events`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsDailyUsage = new HashMap<String, TableInfo.Column>(13);
        _columnsDailyUsage.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("userName", new TableInfo.Column("userName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("anonymousId", new TableInfo.Column("anonymousId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("studyDay", new TableInfo.Column("studyDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("totalScreenTimeMs", new TableInfo.Column("totalScreenTimeMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("appUsageJson", new TableInfo.Column("appUsageJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("totalUnlocks", new TableInfo.Column("totalUnlocks", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("unlockAppsJson", new TableInfo.Column("unlockAppsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("totalNotifications", new TableInfo.Column("totalNotifications", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("notificationsByAppJson", new TableInfo.Column("notificationsByAppJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyUsage.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyUsage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyUsage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyUsage = new TableInfo("daily_usage", _columnsDailyUsage, _foreignKeysDailyUsage, _indicesDailyUsage);
        final TableInfo _existingDailyUsage = TableInfo.read(db, "daily_usage");
        if (!_infoDailyUsage.equals(_existingDailyUsage)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_usage(com.research.usagetracker.DailyUsage).\n"
                  + " Expected:\n" + _infoDailyUsage + "\n"
                  + " Found:\n" + _existingDailyUsage);
        }
        final HashMap<String, TableInfo.Column> _columnsUnlockEvents = new HashMap<String, TableInfo.Column>(5);
        _columnsUnlockEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("appPackage", new TableInfo.Column("appPackage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUnlockEvents.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUnlockEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUnlockEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUnlockEvents = new TableInfo("unlock_events", _columnsUnlockEvents, _foreignKeysUnlockEvents, _indicesUnlockEvents);
        final TableInfo _existingUnlockEvents = TableInfo.read(db, "unlock_events");
        if (!_infoUnlockEvents.equals(_existingUnlockEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "unlock_events(com.research.usagetracker.UnlockEvent).\n"
                  + " Expected:\n" + _infoUnlockEvents + "\n"
                  + " Found:\n" + _existingUnlockEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsNotificationEvents = new HashMap<String, TableInfo.Column>(6);
        _columnsNotificationEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationEvents.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationEvents.put("appPackage", new TableInfo.Column("appPackage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationEvents.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationEvents.put("synced", new TableInfo.Column("synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotificationEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotificationEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotificationEvents = new TableInfo("notification_events", _columnsNotificationEvents, _foreignKeysNotificationEvents, _indicesNotificationEvents);
        final TableInfo _existingNotificationEvents = TableInfo.read(db, "notification_events");
        if (!_infoNotificationEvents.equals(_existingNotificationEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "notification_events(com.research.usagetracker.NotificationEvent).\n"
                  + " Expected:\n" + _infoNotificationEvents + "\n"
                  + " Found:\n" + _existingNotificationEvents);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b6bfb0f61bf671d5b01c78b67332e7bc", "a9bbc157f2a790b213326b344c02a0ce");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "daily_usage","unlock_events","notification_events");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `daily_usage`");
      _db.execSQL("DELETE FROM `unlock_events`");
      _db.execSQL("DELETE FROM `notification_events`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UsageDao.class, UsageDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UsageDao usageDao() {
    if (_usageDao != null) {
      return _usageDao;
    } else {
      synchronized(this) {
        if(_usageDao == null) {
          _usageDao = new UsageDao_Impl(this);
        }
        return _usageDao;
      }
    }
  }
}
