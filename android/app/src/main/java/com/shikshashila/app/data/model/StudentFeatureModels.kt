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
