package com.shikshashila.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CacheEntity::class], version = 1, exportSchema = false)
abstract class ShikshashilaDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}
