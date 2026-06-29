package com.shikshashila.app.data.api

import com.shikshashila.app.data.model.ApiResponse
import com.shikshashila.app.data.model.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ShikshashilaApi {

    @POST("auth/login")
    suspend fun login(@Body body: RequestBody): Response<ApiResponse<LoginResponse>>

    // Student Endpoints
    @GET("student/dashboard")
    suspend fun getStudentDashboard(): Response<ApiResponse<com.shikshashila.app.data.model.StudentDashboardData>>

    @GET("student/attendance")
    suspend fun getStudentAttendance(
        @Query("month") month: Int?,
        @Query("year") year: Int?
    ): Response<ApiResponse<Any>>

    @GET("student/routine")
    suspend fun getStudentRoutine(): Response<ApiResponse<com.shikshashila.app.data.model.RoutineResponse>>

    @GET("student/results")
    suspend fun getStudentResults(): Response<ApiResponse<com.shikshashila.app.data.model.ResultsResponse>>

    @GET("student/homework")
    suspend fun getStudentHomework(): Response<ApiResponse<com.shikshashila.app.data.model.HomeworkResponse>>

    // Teacher Endpoints
    @GET("teacher/dashboard")
    suspend fun getTeacherDashboard(): Response<ApiResponse<com.shikshashila.app.data.model.TeacherDashboardData>>

    @GET("teacher/classes")
    suspend fun getTeacherClasses(): Response<ApiResponse<com.shikshashila.app.data.model.TeacherClassesResponse>>

    @GET("teacher/attendance")
    suspend fun getTeacherAttendance(
        @Query("class_id") classId: String,
        @Query("section_id") sectionId: String,
        @Query("date") date: String
    ): Response<ApiResponse<com.shikshashila.app.data.model.TeacherAttendanceResponse>>

    @POST("teacher/attendance")
    suspend fun submitTeacherAttendance(
        @Body request: com.shikshashila.app.data.model.SubmitAttendanceRequest
    ): Response<ApiResponse<Any>>

    @GET("teacher/assignments")
    suspend fun getTeacherAssignments(
        @Query("class_id") classId: String?,
        @Query("section_id") sectionId: String?
    ): Response<ApiResponse<com.shikshashila.app.data.model.TeacherAssignmentsResponse>>

    // Admin Endpoints
    @GET("admin/dashboard")
    suspend fun getAdminDashboard(): Response<ApiResponse<com.shikshashila.app.data.model.AdminDashboardData>>

    @GET("admin/fees_report")
    suspend fun getAdminFeesReport(
        @Query("session") session: String?
    ): Response<ApiResponse<com.shikshashila.app.data.model.FeesReportData>>
}
