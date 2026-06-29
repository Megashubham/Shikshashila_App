package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

// Admin Dashboard Stats
data class AdminInfo(
    @SerializedName("name") val name: String,
    @SerializedName("school_name") val schoolName: String
)

data class AdminStats(
    @SerializedName("total_staff") val totalStaff: Int,
    @SerializedName("teaching_staff") val teachingStaff: Int,
    @SerializedName("non_teaching_staff") val nonTeachingStaff: Int,
    @SerializedName("total_students") val totalStudents: Int
)

data class AdminDashboardData(
    @SerializedName("admin_info") val adminInfo: AdminInfo,
    @SerializedName("stats") val stats: AdminStats
)

// Fees Report
data class FeeSummary(
    @SerializedName("total_expected") val totalExpected: Long,
    @SerializedName("total_collected") val totalCollected: Long,
    @SerializedName("total_due") val totalDue: Long
)

data class FeeTransaction(
    @SerializedName("receipt_no") val receiptNo: String,
    @SerializedName("student_name") val studentName: String,
    @SerializedName("amount") val amount: Long,
    @SerializedName("date") val date: String
)

data class FeesReportData(
    @SerializedName("session") val session: String,
    @SerializedName("summary") val summary: FeeSummary,
    @SerializedName("recent_transactions") val recentTransactions: List<FeeTransaction>
)
