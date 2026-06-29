package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

// Routine (Timetable)
data class RoutineSlot(
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("teacher") val teacher: String
)

data class RoutineResponse(
    @SerializedName("routine") val routine: Map<String, List<RoutineSlot>>
)

// Exam Results
data class ExamSubject(
    @SerializedName("subject_name") val subjectName: String,
    @SerializedName("marks_obtained") val marksObtained: String,
    @SerializedName("total_marks") val totalMarks: String,
    @SerializedName("grade") val grade: String
)

data class ExamResult(
    @SerializedName("exam_type_id") val examTypeId: String,
    @SerializedName("exam_type_name") val examTypeName: String,
    @SerializedName("subjects") val subjects: List<ExamSubject>
)

data class ResultsResponse(
    @SerializedName("results") val results: List<ExamResult>
)

// Homework
data class Homework(
    @SerializedName("id") val id: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("due_date") val dueDate: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("attachment") val attachment: String?
)

data class HomeworkResponse(
    @SerializedName("homework") val homework: List<Homework>
)

// Student Attendance Detail
data class AttendanceRecord(
    @SerializedName("date") val date: String,
    @SerializedName("present") val present: Boolean
)

data class AttendanceSummaryDetail(
    @SerializedName("total") val total: Int,
    @SerializedName("present") val present: Int,
    @SerializedName("absent") val absent: Int,
    @SerializedName("percentage") val percentage: Double
)

data class StudentAttendanceResponse(
    @SerializedName("month") val month: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("summary") val summary: AttendanceSummaryDetail,
    @SerializedName("records") val records: List<AttendanceRecord>
)

// Student Fees
data class FeePayment(
    @SerializedName("receipt_no") val receiptNo: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("date") val date: String,
    @SerializedName("mode") val mode: String,
    @SerializedName("session") val session: String
)

data class StudentFeesResponse(
    @SerializedName("total_paid") val totalPaid: Double,
    @SerializedName("payments") val payments: List<FeePayment>
)

// Student Notes
data class Note(
    @SerializedName("id") val id: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("file_url") val fileUrl: String?
)

data class NotesResponse(
    @SerializedName("notes") val notes: List<Note>
)
