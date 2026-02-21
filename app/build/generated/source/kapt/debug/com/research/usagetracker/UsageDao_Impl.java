package com.research.usagetracker;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UsageDao_Impl implements UsageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyUsage> __insertionAdapterOfDailyUsage;

  private final EntityInsertionAdapter<UnlockEvent> __insertionAdapterOfUnlockEvent;

  private final EntityInsertionAdapter<NotificationEvent> __insertionAdapterOfNotificationEvent;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSynced;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldUnlocks;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldNotifications;

  public UsageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyUsage = new EntityInsertionAdapter<DailyUsage>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `daily_usage` (`id`,`date`,`userName`,`anonymousId`,`studyDay`,`totalScreenTimeMs`,`appUsageJson`,`totalUnlocks`,`unlockAppsJson`,`totalNotifications`,`notificationsByAppJson`,`synced`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyUsage entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDate() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDate());
        }
        if (entity.getUserName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUserName());
        }
        if (entity.getAnonymousId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAnonymousId());
        }
        statement.bindLong(5, entity.getStudyDay());
        statement.bindLong(6, entity.getTotalScreenTimeMs());
        if (entity.getAppUsageJson() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAppUsageJson());
        }
        statement.bindLong(8, entity.getTotalUnlocks());
        if (entity.getUnlockAppsJson() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getUnlockAppsJson());
        }
        statement.bindLong(10, entity.getTotalNotifications());
        if (entity.getNotificationsByAppJson() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotificationsByAppJson());
        }
        final int _tmp = entity.getSynced() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getTimestamp());
      }
    };
    this.__insertionAdapterOfUnlockEvent = new EntityInsertionAdapter<UnlockEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `unlock_events` (`id`,`timestamp`,`date`,`appPackage`,`synced`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UnlockEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getAppPackage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAppPackage());
        }
        final int _tmp = entity.getSynced() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__insertionAdapterOfNotificationEvent = new EntityInsertionAdapter<NotificationEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `notification_events` (`id`,`timestamp`,`date`,`appPackage`,`appName`,`synced`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NotificationEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getAppPackage() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getAppPackage());
        }
        if (entity.getAppName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getAppName());
        }
        final int _tmp = entity.getSynced() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__preparedStmtOfMarkAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE daily_usage SET synced = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldUnlocks = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM unlock_events WHERE date < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldNotifications = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notification_events WHERE date < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertDailyUsage(final DailyUsage usage,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDailyUsage.insertAndReturnId(usage);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertUnlockEvent(final UnlockEvent event,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUnlockEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertNotificationEvent(final NotificationEvent event,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNotificationEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldUnlocks(final String date, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldUnlocks.acquire();
        int _argIndex = 1;
        if (date == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, date);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldUnlocks.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldNotifications(final String date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldNotifications.acquire();
        int _argIndex = 1;
        if (date == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, date);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldNotifications.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getRecentUsage(final Continuation<? super List<DailyUsage>> $completion) {
    final String _sql = "SELECT * FROM daily_usage ORDER BY timestamp DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DailyUsage>>() {
      @Override
      @NonNull
      public List<DailyUsage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfAnonymousId = CursorUtil.getColumnIndexOrThrow(_cursor, "anonymousId");
          final int _cursorIndexOfStudyDay = CursorUtil.getColumnIndexOrThrow(_cursor, "studyDay");
          final int _cursorIndexOfTotalScreenTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScreenTimeMs");
          final int _cursorIndexOfAppUsageJson = CursorUtil.getColumnIndexOrThrow(_cursor, "appUsageJson");
          final int _cursorIndexOfTotalUnlocks = CursorUtil.getColumnIndexOrThrow(_cursor, "totalUnlocks");
          final int _cursorIndexOfUnlockAppsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockAppsJson");
          final int _cursorIndexOfTotalNotifications = CursorUtil.getColumnIndexOrThrow(_cursor, "totalNotifications");
          final int _cursorIndexOfNotificationsByAppJson = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsByAppJson");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<DailyUsage> _result = new ArrayList<DailyUsage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyUsage _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpAnonymousId;
            if (_cursor.isNull(_cursorIndexOfAnonymousId)) {
              _tmpAnonymousId = null;
            } else {
              _tmpAnonymousId = _cursor.getString(_cursorIndexOfAnonymousId);
            }
            final int _tmpStudyDay;
            _tmpStudyDay = _cursor.getInt(_cursorIndexOfStudyDay);
            final long _tmpTotalScreenTimeMs;
            _tmpTotalScreenTimeMs = _cursor.getLong(_cursorIndexOfTotalScreenTimeMs);
            final String _tmpAppUsageJson;
            if (_cursor.isNull(_cursorIndexOfAppUsageJson)) {
              _tmpAppUsageJson = null;
            } else {
              _tmpAppUsageJson = _cursor.getString(_cursorIndexOfAppUsageJson);
            }
            final int _tmpTotalUnlocks;
            _tmpTotalUnlocks = _cursor.getInt(_cursorIndexOfTotalUnlocks);
            final String _tmpUnlockAppsJson;
            if (_cursor.isNull(_cursorIndexOfUnlockAppsJson)) {
              _tmpUnlockAppsJson = null;
            } else {
              _tmpUnlockAppsJson = _cursor.getString(_cursorIndexOfUnlockAppsJson);
            }
            final int _tmpTotalNotifications;
            _tmpTotalNotifications = _cursor.getInt(_cursorIndexOfTotalNotifications);
            final String _tmpNotificationsByAppJson;
            if (_cursor.isNull(_cursorIndexOfNotificationsByAppJson)) {
              _tmpNotificationsByAppJson = null;
            } else {
              _tmpNotificationsByAppJson = _cursor.getString(_cursorIndexOfNotificationsByAppJson);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new DailyUsage(_tmpId,_tmpDate,_tmpUserName,_tmpAnonymousId,_tmpStudyDay,_tmpTotalScreenTimeMs,_tmpAppUsageJson,_tmpTotalUnlocks,_tmpUnlockAppsJson,_tmpTotalNotifications,_tmpNotificationsByAppJson,_tmpSynced,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUsageByDate(final String date,
      final Continuation<? super DailyUsage> $completion) {
    final String _sql = "SELECT * FROM daily_usage WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyUsage>() {
      @Override
      @Nullable
      public DailyUsage call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfAnonymousId = CursorUtil.getColumnIndexOrThrow(_cursor, "anonymousId");
          final int _cursorIndexOfStudyDay = CursorUtil.getColumnIndexOrThrow(_cursor, "studyDay");
          final int _cursorIndexOfTotalScreenTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScreenTimeMs");
          final int _cursorIndexOfAppUsageJson = CursorUtil.getColumnIndexOrThrow(_cursor, "appUsageJson");
          final int _cursorIndexOfTotalUnlocks = CursorUtil.getColumnIndexOrThrow(_cursor, "totalUnlocks");
          final int _cursorIndexOfUnlockAppsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockAppsJson");
          final int _cursorIndexOfTotalNotifications = CursorUtil.getColumnIndexOrThrow(_cursor, "totalNotifications");
          final int _cursorIndexOfNotificationsByAppJson = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsByAppJson");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final DailyUsage _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpAnonymousId;
            if (_cursor.isNull(_cursorIndexOfAnonymousId)) {
              _tmpAnonymousId = null;
            } else {
              _tmpAnonymousId = _cursor.getString(_cursorIndexOfAnonymousId);
            }
            final int _tmpStudyDay;
            _tmpStudyDay = _cursor.getInt(_cursorIndexOfStudyDay);
            final long _tmpTotalScreenTimeMs;
            _tmpTotalScreenTimeMs = _cursor.getLong(_cursorIndexOfTotalScreenTimeMs);
            final String _tmpAppUsageJson;
            if (_cursor.isNull(_cursorIndexOfAppUsageJson)) {
              _tmpAppUsageJson = null;
            } else {
              _tmpAppUsageJson = _cursor.getString(_cursorIndexOfAppUsageJson);
            }
            final int _tmpTotalUnlocks;
            _tmpTotalUnlocks = _cursor.getInt(_cursorIndexOfTotalUnlocks);
            final String _tmpUnlockAppsJson;
            if (_cursor.isNull(_cursorIndexOfUnlockAppsJson)) {
              _tmpUnlockAppsJson = null;
            } else {
              _tmpUnlockAppsJson = _cursor.getString(_cursorIndexOfUnlockAppsJson);
            }
            final int _tmpTotalNotifications;
            _tmpTotalNotifications = _cursor.getInt(_cursorIndexOfTotalNotifications);
            final String _tmpNotificationsByAppJson;
            if (_cursor.isNull(_cursorIndexOfNotificationsByAppJson)) {
              _tmpNotificationsByAppJson = null;
            } else {
              _tmpNotificationsByAppJson = _cursor.getString(_cursorIndexOfNotificationsByAppJson);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new DailyUsage(_tmpId,_tmpDate,_tmpUserName,_tmpAnonymousId,_tmpStudyDay,_tmpTotalScreenTimeMs,_tmpAppUsageJson,_tmpTotalUnlocks,_tmpUnlockAppsJson,_tmpTotalNotifications,_tmpNotificationsByAppJson,_tmpSynced,_tmpTimestamp);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedUsage(final Continuation<? super List<DailyUsage>> $completion) {
    final String _sql = "SELECT * FROM daily_usage WHERE synced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DailyUsage>>() {
      @Override
      @NonNull
      public List<DailyUsage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfAnonymousId = CursorUtil.getColumnIndexOrThrow(_cursor, "anonymousId");
          final int _cursorIndexOfStudyDay = CursorUtil.getColumnIndexOrThrow(_cursor, "studyDay");
          final int _cursorIndexOfTotalScreenTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScreenTimeMs");
          final int _cursorIndexOfAppUsageJson = CursorUtil.getColumnIndexOrThrow(_cursor, "appUsageJson");
          final int _cursorIndexOfTotalUnlocks = CursorUtil.getColumnIndexOrThrow(_cursor, "totalUnlocks");
          final int _cursorIndexOfUnlockAppsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockAppsJson");
          final int _cursorIndexOfTotalNotifications = CursorUtil.getColumnIndexOrThrow(_cursor, "totalNotifications");
          final int _cursorIndexOfNotificationsByAppJson = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsByAppJson");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<DailyUsage> _result = new ArrayList<DailyUsage>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyUsage _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpAnonymousId;
            if (_cursor.isNull(_cursorIndexOfAnonymousId)) {
              _tmpAnonymousId = null;
            } else {
              _tmpAnonymousId = _cursor.getString(_cursorIndexOfAnonymousId);
            }
            final int _tmpStudyDay;
            _tmpStudyDay = _cursor.getInt(_cursorIndexOfStudyDay);
            final long _tmpTotalScreenTimeMs;
            _tmpTotalScreenTimeMs = _cursor.getLong(_cursorIndexOfTotalScreenTimeMs);
            final String _tmpAppUsageJson;
            if (_cursor.isNull(_cursorIndexOfAppUsageJson)) {
              _tmpAppUsageJson = null;
            } else {
              _tmpAppUsageJson = _cursor.getString(_cursorIndexOfAppUsageJson);
            }
            final int _tmpTotalUnlocks;
            _tmpTotalUnlocks = _cursor.getInt(_cursorIndexOfTotalUnlocks);
            final String _tmpUnlockAppsJson;
            if (_cursor.isNull(_cursorIndexOfUnlockAppsJson)) {
              _tmpUnlockAppsJson = null;
            } else {
              _tmpUnlockAppsJson = _cursor.getString(_cursorIndexOfUnlockAppsJson);
            }
            final int _tmpTotalNotifications;
            _tmpTotalNotifications = _cursor.getInt(_cursorIndexOfTotalNotifications);
            final String _tmpNotificationsByAppJson;
            if (_cursor.isNull(_cursorIndexOfNotificationsByAppJson)) {
              _tmpNotificationsByAppJson = null;
            } else {
              _tmpNotificationsByAppJson = _cursor.getString(_cursorIndexOfNotificationsByAppJson);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new DailyUsage(_tmpId,_tmpDate,_tmpUserName,_tmpAnonymousId,_tmpStudyDay,_tmpTotalScreenTimeMs,_tmpAppUsageJson,_tmpTotalUnlocks,_tmpUnlockAppsJson,_tmpTotalNotifications,_tmpNotificationsByAppJson,_tmpSynced,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnlocksByDate(final String date,
      final Continuation<? super List<UnlockEvent>> $completion) {
    final String _sql = "SELECT * FROM unlock_events WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UnlockEvent>>() {
      @Override
      @NonNull
      public List<UnlockEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfAppPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "appPackage");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final List<UnlockEvent> _result = new ArrayList<UnlockEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpAppPackage;
            if (_cursor.isNull(_cursorIndexOfAppPackage)) {
              _tmpAppPackage = null;
            } else {
              _tmpAppPackage = _cursor.getString(_cursorIndexOfAppPackage);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            _item = new UnlockEvent(_tmpId,_tmpTimestamp,_tmpDate,_tmpAppPackage,_tmpSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnlockCountByDate(final String date,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM unlock_events WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getNotificationsByDate(final String date,
      final Continuation<? super List<NotificationEvent>> $completion) {
    final String _sql = "SELECT * FROM notification_events WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NotificationEvent>>() {
      @Override
      @NonNull
      public List<NotificationEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfAppPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "appPackage");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final List<NotificationEvent> _result = new ArrayList<NotificationEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotificationEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpAppPackage;
            if (_cursor.isNull(_cursorIndexOfAppPackage)) {
              _tmpAppPackage = null;
            } else {
              _tmpAppPackage = _cursor.getString(_cursorIndexOfAppPackage);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            _item = new NotificationEvent(_tmpId,_tmpTimestamp,_tmpDate,_tmpAppPackage,_tmpAppName,_tmpSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getNotificationCountByDate(final String date,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM notification_events WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnlocksBetween(final long startTime, final long endTime,
      final Continuation<? super List<UnlockEvent>> $completion) {
    final String _sql = "SELECT * FROM unlock_events WHERE timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UnlockEvent>>() {
      @Override
      @NonNull
      public List<UnlockEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfAppPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "appPackage");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final List<UnlockEvent> _result = new ArrayList<UnlockEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UnlockEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpAppPackage;
            if (_cursor.isNull(_cursorIndexOfAppPackage)) {
              _tmpAppPackage = null;
            } else {
              _tmpAppPackage = _cursor.getString(_cursorIndexOfAppPackage);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            _item = new UnlockEvent(_tmpId,_tmpTimestamp,_tmpDate,_tmpAppPackage,_tmpSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getNotificationsBetween(final long startTime, final long endTime,
      final Continuation<? super List<NotificationEvent>> $completion) {
    final String _sql = "SELECT * FROM notification_events WHERE timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<NotificationEvent>>() {
      @Override
      @NonNull
      public List<NotificationEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfAppPackage = CursorUtil.getColumnIndexOrThrow(_cursor, "appPackage");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "synced");
          final List<NotificationEvent> _result = new ArrayList<NotificationEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotificationEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpAppPackage;
            if (_cursor.isNull(_cursorIndexOfAppPackage)) {
              _tmpAppPackage = null;
            } else {
              _tmpAppPackage = _cursor.getString(_cursorIndexOfAppPackage);
            }
            final String _tmpAppName;
            if (_cursor.isNull(_cursorIndexOfAppName)) {
              _tmpAppName = null;
            } else {
              _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            }
            final boolean _tmpSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSynced);
            _tmpSynced = _tmp != 0;
            _item = new NotificationEvent(_tmpId,_tmpTimestamp,_tmpDate,_tmpAppPackage,_tmpAppName,_tmpSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
