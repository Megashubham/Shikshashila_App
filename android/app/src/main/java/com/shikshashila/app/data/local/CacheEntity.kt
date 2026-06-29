package com.shikshashila.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "api_cache")
data class CacheEntity(
    @PrimaryKey
    val cacheKey: String, // e.g., "student_routine", "teacher_dashboard"
    val jsonResponse: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
