package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

// ─── Students ───────────────────────────────────────────────────────────────

data class AdminStudent(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("name") val name: String,
    @SerializedName("registration_no") val registrationNo: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("class_name") val className: String,
    @SerializedName("section_name") val sectionName: String,
    @SerializedName("photo_url") val photoUrl: String?
)

data class AdminStudentsResponse(
    @SerializedName("students") val students: List<AdminStudent>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int
)

// ─── Staff ──────────────────────────────────────────────────────────────────

data class AdminStaff(
    @SerializedName("staff_id") val staffId: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("designation") val designation: String,
    @SerializedName("type") val type: String,
    @SerializedName("photo_url") val photoUrl: String?
)

data class AdminStaffResponse(
    @SerializedName("staff") val staff: List<AdminStaff>,
    @SerializedName("total") val total: Int
)

// ─── Attendance ──────────────────────────────────────────────────────────────

data class AttendanceSummary(
    @SerializedName("total_marked") val totalMarked: Int,
    @SerializedName("present") val present: Int,
    @SerializedName("absent") val absent: Int
)

data class ClassAttendance(
    @SerializedName("class_name") val className: String,
    @SerializedName("total") val total: Int,
    @SerializedName("present") val present: Int,
    @SerializedName("absent") val absent: Int,
    @SerializedName("percentage") val percentage: Double
)

data class AdminAttendanceResponse(
    @SerializedName("date") val date: String,
    @SerializedName("enrolled") val enrolled: Int,
    @SerializedName("summary") val summary: AttendanceSummary,
    @SerializedName("by_class") val byClass: List<ClassAttendance>
)

// ─── Classes ─────────────────────────────────────────────────────────────────

data class ClassSection(
    @SerializedName("section_id") val sectionId: String,
    @SerializedName("section_name") val sectionName: String
)

data class AdminClass(
    @SerializedName("class_id") val classId: String,
    @SerializedName("class_name") val className: String,
    @SerializedName("section_count") val sectionCount: Int,
    @SerializedName("student_count") val studentCount: Int,
    @SerializedName("sections") val sections: List<ClassSection>
)

data class AdminClassesResponse(
    @SerializedName("classes") val classes: List<AdminClass>,
    @SerializedName("total") val total: Int
)
