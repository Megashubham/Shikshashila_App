package com.shikshashila.app.data.repository

import com.google.gson.Gson
import com.shikshashila.app.data.api.ShikshashilaApi
import com.shikshashila.app.data.local.CacheDao
import com.shikshashila.app.data.local.CacheEntity
import com.shikshashila.app.data.model.SubmitAttendanceRequest
import com.shikshashila.app.data.model.TeacherAssignmentsResponse
import com.shikshashila.app.data.model.TeacherAttendanceResponse
import com.shikshashila.app.data.model.TeacherClassesResponse
import com.shikshashila.app.data.model.TeacherDashboardData
import com.shikshashila.app.domain.repository.TeacherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeacherRepositoryImpl @Inject constructor(
    private val api: ShikshashilaApi,
    private val cacheDao: CacheDao,
    private val gson: Gson
) : TeacherRepository {

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

    override suspend fun getDashboard(): Result<TeacherDashboardData> {
        val cacheKey = "teacher_dashboard"
        return try {
            val response = api.getTeacherDashboard()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<TeacherDashboardData>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load dashboard"))
            }
        } catch (e: Exception) {
            fetchFromCache<TeacherDashboardData>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getClasses(): Result<TeacherClassesResponse> {
        val cacheKey = "teacher_classes"
        return try {
            val response = api.getTeacherClasses()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<TeacherClassesResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load classes"))
            }
        } catch (e: Exception) {
            fetchFromCache<TeacherClassesResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getAttendance(classId: String, sectionId: String, date: String): Result<TeacherAttendanceResponse> {
        val cacheKey = "teacher_attendance_${classId}_${sectionId}_$date"
        return try {
            val response = api.getTeacherAttendance(classId, sectionId, date)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<TeacherAttendanceResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load students"))
            }
        } catch (e: Exception) {
            fetchFromCache<TeacherAttendanceResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun submitAttendance(request: SubmitAttendanceRequest): Result<String> {
        // We don't cache POST actions
        return try {
            val response = api.submitTeacherAttendance(request)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess) {
                Result.success(body.message ?: "Attendance submitted successfully")
            } else {
                Result.failure(Exception(body?.message ?: "Failed to submit attendance"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        }
    }

    override suspend fun getAssignments(classId: String?, sectionId: String?): Result<TeacherAssignmentsResponse> {
        val cacheKey = "teacher_assignments_${classId ?: "all"}_${sectionId ?: "all"}"
        return try {
            val response = api.getTeacherAssignments(classId, sectionId)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<TeacherAssignmentsResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load assignments"))
            }
        } catch (e: Exception) {
            fetchFromCache<TeacherAssignmentsResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getTeacherRoutine(): Result<com.shikshashila.app.data.model.TeacherRoutineResponse> {
        val cacheKey = "teacher_routine"
        return try {
            val response = api.getTeacherRoutine()
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<com.shikshashila.app.data.model.TeacherRoutineResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load routine"))
            }
        } catch (e: Exception) {
            fetchFromCache<com.shikshashila.app.data.model.TeacherRoutineResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }

    override suspend fun getStudentsList(classId: String, sectionId: String): Result<com.shikshashila.app.data.model.TeacherStudentsListResponse> {
        val cacheKey = "teacher_students_${classId}_${sectionId}"
        return try {
            val response = api.getTeacherStudents(classId, sectionId)
            val body = response.body()
            if (response.isSuccessful && body != null && body.isSuccess && body.data != null) {
                cacheDao.insertCache(CacheEntity(cacheKey, gson.toJson(body.data)))
                Result.success(body.data)
            } else {
                fetchFromCache<com.shikshashila.app.data.model.TeacherStudentsListResponse>(cacheKey)
                    ?: Result.failure(Exception(body?.message ?: "Failed to load students"))
            }
        } catch (e: Exception) {
            fetchFromCache<com.shikshashila.app.data.model.TeacherStudentsListResponse>(cacheKey)
                ?: Result.failure(Exception("Network error, and no offline data available."))
        }
    }
}
