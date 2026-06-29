package com.shikshashila.app.ui.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.TeacherDashboardData
import com.shikshashila.app.domain.repository.AuthRepository
import com.shikshashila.app.domain.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TeacherDashboardState {
    object Loading : TeacherDashboardState()
    data class Success(val data: TeacherDashboardData) : TeacherDashboardState()
    data class Error(val message: String) : TeacherDashboardState()
}

@HiltViewModel
class TeacherViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TeacherDashboardState>(TeacherDashboardState.Loading)
    val uiState: StateFlow<TeacherDashboardState> = _uiState.asStateFlow()

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        _uiState.value = TeacherDashboardState.Loading
        viewModelScope.launch {
            val result = teacherRepository.getDashboard()
            result.onSuccess { data ->
                _uiState.value = TeacherDashboardState.Success(data)
            }.onFailure { error ->
                _uiState.value = TeacherDashboardState.Error(error.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
