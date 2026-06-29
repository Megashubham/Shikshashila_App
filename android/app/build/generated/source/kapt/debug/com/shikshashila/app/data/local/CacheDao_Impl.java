package com.shikshashila.app.data.local;

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
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CacheDao_Impl implements CacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CacheEntity> __insertionAdapterOfCacheEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAllCache;

  public CacheDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCacheEntity = new EntityInsertionAdapter<CacheEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `api_cache` (`cacheKey`,`jsonResponse`,`lastUpdated`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CacheEntity entity) {
        if (entity.getCacheKey() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getCacheKey());
        }
        if (entity.getJsonResponse() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getJsonResponse());
        }
        statement.bindLong(3, entity.getLastUpdated());
      }
    };
    this.__preparedStmtOfClearAllCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM api_cache";
        return _query;
      }
    };
  }

  @Override
  public Object insertCache(final CacheEntity cache, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCacheEntity.insert(cache);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllCache(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllCache.acquire();
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
          __preparedStmtOfClearAllCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getCache(final String key, final Continuation<? super CacheEntity> $completion) {
    final String _sql = "SELECT * FROM api_cache WHERE cacheKey = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CacheEntity>() {
      @Override
      @Nullable
      public CacheEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCacheKey = CursorUtil.getColumnIndexOrThrow(_cursor, "cacheKey");
          final int _cursorIndexOfJsonResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "jsonResponse");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final CacheEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpCacheKey;
            if (_cursor.isNull(_cursorIndexOfCacheKey)) {
              _tmpCacheKey = null;
            } else {
              _tmpCacheKey = _cursor.getString(_cursorIndexOfCacheKey);
            }
            final String _tmpJsonResponse;
            if (_cursor.isNull(_cursorIndexOfJsonResponse)) {
              _tmpJsonResponse = null;
            } else {
              _tmpJsonResponse = _cursor.getString(_cursorIndexOfJsonResponse);
            }
            final long _tmpLastUpdated;
            _tmpLastUpdated = _cursor.getLong(_cursorIndexOfLastUpdated);
            _result = new CacheEntity(_tmpCacheKey,_tmpJsonResponse,_tmpLastUpdated);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
