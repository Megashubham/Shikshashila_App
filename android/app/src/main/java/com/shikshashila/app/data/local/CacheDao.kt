package com.shikshashila.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cache: CacheEntity)

    @Query("SELECT * FROM api_cache WHERE cacheKey = :key LIMIT 1")
    suspend fun getCache(key: String): CacheEntity?

    @Query("DELETE FROM api_cache")
    suspend fun clearAllCache()
}
