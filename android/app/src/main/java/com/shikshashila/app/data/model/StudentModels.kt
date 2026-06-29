package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

data class StudentInfo(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("name") val name: String,
    @SerializedName("registration_no") val registrationNo: String,
    @SerializedName("class_name") val className: String,
    @SerializedName("section_name") val sectionName: String
)

data class AttendanceMonth(
    @SerializedName("total") val total: Int,
    @SerializedName("present") val present: Int,
    @SerializedName("absent") val absent: Int,
    @SerializedName("percentage") val percentage: Double
)

data class StudentAttendance(
    @SerializedName("current_month") val currentMonth: AttendanceMonth
)

data class StudentDashboardData(
    @SerializedName("student_info") val studentInfo: StudentInfo,
    @SerializedName("attendance") val attendance: StudentAttendance
)
