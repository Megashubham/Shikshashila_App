package com.shikshashila.app.domain.repository

import com.shikshashila.app.data.model.SubmitAttendanceRequest
import com.shikshashila.app.data.model.TeacherAssignmentsResponse
import com.shikshashila.app.data.model.TeacherAttendanceResponse
import com.shikshashila.app.data.model.TeacherClassesResponse
import com.shikshashila.app.data.model.TeacherDashboardData

interface TeacherRepository {
    suspend fun getDashboard(): Result<TeacherDashboardData>
    suspend fun getClasses(): Result<TeacherClassesResponse>
    suspend fun getAttendance(classId: String, sectionId: String, date: String): Result<TeacherAttendanceResponse>
    suspend fun submitAttendance(request: SubmitAttendanceRequest): Result<String>
    suspend fun getAssignments(classId: String?, sectionId: String?): Result<TeacherAssignmentsResponse>
}
