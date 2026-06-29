package com.shikshashila.app.domain.repository

import com.shikshashila.app.data.model.HomeworkResponse
import com.shikshashila.app.data.model.ResultsResponse
import com.shikshashila.app.data.model.RoutineResponse
import com.shikshashila.app.data.model.StudentDashboardData

interface StudentRepository {
    suspend fun getDashboard(): Result<StudentDashboardData>
    suspend fun getRoutine(): Result<RoutineResponse>
    suspend fun getResults(): Result<ResultsResponse>
    suspend fun getHomework(): Result<HomeworkResponse>
    suspend fun getAttendance(month: Int? = null, year: Int? = null): Result<com.shikshashila.app.data.model.StudentAttendanceResponse>
    suspend fun getFees(): Result<com.shikshashila.app.data.model.StudentFeesResponse>
    suspend fun getNotes(): Result<com.shikshashila.app.data.model.NotesResponse>
}
