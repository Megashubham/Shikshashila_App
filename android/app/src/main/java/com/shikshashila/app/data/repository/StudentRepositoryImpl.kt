package com.shikshashila.app.data.repository

import com.google.gson.Gson
import com.shikshashila.app.data.api.ShikshashilaApi
import com.shikshashila.app.data.local.CacheDao
import com.shikshashila.app.data.local.CacheEntity
import com.shikshashila.app.data.model.StudentDashboardData
import com.shikshashila.app.domain.repository.StudentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val api: ShikshashilaApi,
    private val cacheDao: CacheDao,
    private val gson: Gson
) : StudentRepository {

    override suspend fun getDashboard(): Result<StudentDashboardData> {
        val cacheKey = "student_dashboard"
        return try {
            val response = api.getStudentDashboard()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<StudentDashboardData>(cacheKey) 
                    ?: Result.failure(Exception(body?.message ?: "Failed to load dashboard"))
            }
        } catch (e: Exception) {
            fetchFromCache<StudentDashboardData>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getRoutine(): Result<com.shikshashila.app.data.model.RoutineResponse> {
        val cacheKey = "student_routine"
        return try {
            val response = api.getStudentRoutine()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<com.shikshashila.app.data.model.RoutineResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load routine"))
            }
        } catch (e: Exception) {
            fetchFromCache<com.shikshashila.app.data.model.RoutineResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getResults(): Result<com.shikshashila.app.data.model.ResultsResponse> {
        val cacheKey = "student_results"
        return try {
            val response = api.getStudentResults()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<com.shikshashila.app.data.model.ResultsResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load results"))
            }
        } catch (e: Exception) {
            fetchFromCache<com.shikshashila.app.data.model.ResultsResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getHomework(): Result<com.shikshashila.app.data.model.HomeworkResponse> {
        val cacheKey = "student_homework"
        return try {
            val response = api.getStudentHomework()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<com.shikshashila.app.data.model.HomeworkResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load homework"))
            }
        } catch (e: Exception) {
            fetchFromCache<com.shikshashila.app.data.model.HomeworkResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }
    
    private suspend inline fun <reified T> fetchFromCache(key: String): Result<T>? {
        return try {
            val cached = cacheDao.getCache(key)
            if (cached != null) {
                val data = gson.fromJson(cached.jsonResponse, T::class.java)
                Result.success(data)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
