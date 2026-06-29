package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

// Classes
data class TeacherClass(
    @SerializedName("class_id") val classId: String,
    @SerializedName("section_id") val sectionId: String,
    @SerializedName("class_name") val className: String,
    @SerializedName("section_name") val sectionName: String,
    @SerializedName("display") val display: String
)

data class TeacherClassesResponse(
    @SerializedName("classes") val classes: List<TeacherClass>
)

// Students & Attendance
data class StudentAttendanceRecord(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("name") val name: String,
    @SerializedName("registration_no") val registrationNo: String,
    @SerializedName("status") val status: String // "present", "absent", "not_marked"
)

data class TeacherAttendanceResponse(
    @SerializedName("date") val date: String,
    @SerializedName("students") val students: List<StudentAttendanceRecord>
)

data class SubmitAttendanceRequest(
    @SerializedName("class_id") val classId: String,
    @SerializedName("section_id") val sectionId: String,
    @SerializedName("date") val date: String,
    @SerializedName("records") val records: List<AttendanceSubmission>
)

data class AttendanceSubmission(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("present") val present: Boolean
)

// Assignments
data class TeacherAssignment(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("class") val className: String,
    @SerializedName("attachment") val attachment: String?
)

data class TeacherAssignmentsResponse(
    @SerializedName("assignments") val assignments: List<TeacherAssignment>
)

// Teacher Timetable (Routine)
data class TRoutineSlot(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("class_name") val className: String
)

data class TeacherRoutineResponse(
    @SerializedName("routine") val routine: Map<String, List<TRoutineSlot>>
)

// Teacher Students List
data class TeacherStudentItem(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("name") val name: String,
    @SerializedName("registration_no") val registrationNo: String
)

data class TeacherStudentsListResponse(
    @SerializedName("students") val students: List<TeacherStudentItem>
)
