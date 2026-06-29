package com.shikshashila.app.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.StudentDashboardData
import com.shikshashila.app.domain.repository.AuthRepository
import com.shikshashila.app.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val data: StudentDashboardData) : DashboardState()
    data class Error(val message: String) : DashboardState()
}

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        _uiState.value = DashboardState.Loading
        viewModelScope.launch {
            val result = studentRepository.getDashboard()
            result.onSuccess { data ->
                _uiState.value = DashboardState.Success(data)
            }.onFailure { error ->
                _uiState.value = DashboardState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
