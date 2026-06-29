package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

data class TeacherInfo(
    @SerializedName("name") val name: String,
    @SerializedName("designation") val designation: String
)

data class TeacherStats(
    @SerializedName("assigned_classes") val assignedClasses: Int,
    @SerializedName("assigned_subjects") val assignedSubjects: Int
)

data class TodaySchedule(
    @SerializedName("class") val className: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String
)

data class TeacherDashboardData(
    @SerializedName("teacher_info") val teacherInfo: TeacherInfo,
    @SerializedName("stats") val stats: TeacherStats,
    @SerializedName("today_schedule") val todaySchedule: List<TodaySchedule>
)
