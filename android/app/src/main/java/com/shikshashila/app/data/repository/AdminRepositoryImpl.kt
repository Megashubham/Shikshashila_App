package com.shikshashila.app.data.repository

import com.google.gson.Gson
import com.shikshashila.app.data.api.ShikshashilaApi
import com.shikshashila.app.data.local.CacheDao
import com.shikshashila.app.data.local.CacheEntity
import com.shikshashila.app.data.model.AdminDashboardData
import com.shikshashila.app.data.model.FeesReportData
import com.shikshashila.app.domain.repository.AdminRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepositoryImpl @Inject constructor(
    private val api: ShikshashilaApi,
    private val cacheDao: CacheDao,
    private val gson: Gson
) : AdminRepository {

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

    override suspend fun getDashboard(): Result<AdminDashboardData> {
        val cacheKey = "admin_dashboard"
        return try {
            val response = api.getAdminDashboard()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                val errorString = response.errorBody()?.string()
                var errorMsg = body?.message ?: "Failed to load admin dashboard"
                if (!errorString.isNullOrEmpty()) {
                    try {
                        val json = org.json.JSONObject(errorString)
                        if (json.has("message")) errorMsg = json.getString("message")
                    } catch (e: Exception) {}
                }
                
                fetchFromCache<AdminDashboardData>(cacheKey)
                    ?: Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            fetchFromCache<AdminDashboardData>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getFeesReport(session: String?): Result<FeesReportData> {
        val cacheKey = "admin_fees_report_${session ?: "current"}"
        return try {
            val response = api.getAdminFeesReport(session)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                val errorString = response.errorBody()?.string()
                var errorMsg = body?.message ?: "Failed to load fee report"
                if (!errorString.isNullOrEmpty()) {
                    try {
                        val json = org.json.JSONObject(errorString)
                        if (json.has("message")) errorMsg = json.getString("message")
                    } catch (e: Exception) {}
                }
                
                fetchFromCache<FeesReportData>(cacheKey)
                    ?: Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            fetchFromCache<FeesReportData>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }
}
