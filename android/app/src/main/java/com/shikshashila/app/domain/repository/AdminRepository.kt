package com.shikshashila.app.domain.repository

import com.shikshashila.app.data.model.AdminDashboardData
import com.shikshashila.app.data.model.FeesReportData

interface AdminRepository {
    suspend fun getDashboard(): Result<AdminDashboardData>
    suspend fun getFeesReport(session: String? = null): Result<FeesReportData>
}
