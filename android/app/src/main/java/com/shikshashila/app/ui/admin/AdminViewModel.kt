package com.shikshashila.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.AdminDashboardData
import com.shikshashila.app.data.model.FeesReportData
import com.shikshashila.app.domain.repository.AdminRepository
import com.shikshashila.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminState<out T> {
    object Idle : AdminState<Nothing>()
    object Loading : AdminState<Nothing>()
    data class Success<T>(val data: T) : AdminState<T>()
    data class Error(val message: String) : AdminState<Nothing>()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _dashboardState = MutableStateFlow<AdminState<AdminDashboardData>>(AdminState.Idle)
    val dashboardState: StateFlow<AdminState<AdminDashboardData>> = _dashboardState.asStateFlow()

    private val _feesReportState = MutableStateFlow<AdminState<FeesReportData>>(AdminState.Idle)
    val feesReportState: StateFlow<AdminState<FeesReportData>> = _feesReportState.asStateFlow()

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        _dashboardState.value = AdminState.Loading
        viewModelScope.launch {
            adminRepository.getDashboard()
                .onSuccess { _dashboardState.value = AdminState.Success(it) }
                .onFailure { _dashboardState.value = AdminState.Error(it.message ?: "Failed to load dashboard") }
        }
    }

    fun fetchFeesReport(session: String? = null) {
        _feesReportState.value = AdminState.Loading
        viewModelScope.launch {
            adminRepository.getFeesReport(session)
                .onSuccess { _feesReportState.value = AdminState.Success(it) }
                .onFailure { _feesReportState.value = AdminState.Error(it.message ?: "Failed to load fees report") }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
