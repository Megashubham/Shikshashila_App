package com.shikshashila.app.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.HomeworkResponse
import com.shikshashila.app.data.model.NotesResponse
import com.shikshashila.app.data.model.ResultsResponse
import com.shikshashila.app.data.model.RoutineResponse
import com.shikshashila.app.data.model.StudentAttendanceResponse
import com.shikshashila.app.data.model.StudentFeesResponse
import com.shikshashila.app.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FeatureState<out T> {
    object Idle : FeatureState<Nothing>()
    object Loading : FeatureState<Nothing>()
    data class Success<T>(val data: T) : FeatureState<T>()
    data class Error(val message: String) : FeatureState<Nothing>()
}

@HiltViewModel
class StudentFeatureViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    // ── Routine ──────────────────────────────────────────────────────────────
    private val _routineState = MutableStateFlow<FeatureState<RoutineResponse>>(FeatureState.Idle)
    val routineState: StateFlow<FeatureState<RoutineResponse>> = _routineState.asStateFlow()

    // ── Results ───────────────────────────────────────────────────────────────
    private val _resultsState = MutableStateFlow<FeatureState<ResultsResponse>>(FeatureState.Idle)
    val resultsState: StateFlow<FeatureState<ResultsResponse>> = _resultsState.asStateFlow()

    // ── Homework ──────────────────────────────────────────────────────────────
    private val _homeworkState = MutableStateFlow<FeatureState<HomeworkResponse>>(FeatureState.Idle)
    val homeworkState: StateFlow<FeatureState<HomeworkResponse>> = _homeworkState.asStateFlow()

    // ── Attendance ────────────────────────────────────────────────────────────
    private val _attendanceState = MutableStateFlow<FeatureState<StudentAttendanceResponse>>(FeatureState.Idle)
    val attendanceState: StateFlow<FeatureState<StudentAttendanceResponse>> = _attendanceState.asStateFlow()

    // ── Fees ──────────────────────────────────────────────────────────────────
    private val _feesState = MutableStateFlow<FeatureState<StudentFeesResponse>>(FeatureState.Idle)
    val feesState: StateFlow<FeatureState<StudentFeesResponse>> = _feesState.asStateFlow()

    // ── Notes ─────────────────────────────────────────────────────────────────
    private val _notesState = MutableStateFlow<FeatureState<NotesResponse>>(FeatureState.Idle)
    val notesState: StateFlow<FeatureState<NotesResponse>> = _notesState.asStateFlow()

    // ── Fetch methods ─────────────────────────────────────────────────────────

    fun fetchRoutine() {
        _routineState.value = FeatureState.Loading
        viewModelScope.launch {
            repository.getRoutine()
                .onSuccess { _routineState.value = FeatureState.Success(it) }
                .onFailure { _routineState.value = FeatureState.Error(it.message ?: "Error fetching routine") }
        }
    }

    fun fetchResults() {
        _resultsState.value = FeatureState.Loading
        viewModelScope.launch {
            repository.getResults()
                .onSuccess { _resultsState.value = FeatureState.Success(it) }
                .onFailure { _resultsState.value = FeatureState.Error(it.message ?: "Error fetching results") }
        }
    }

    fun fetchHomework() {
        _homeworkState.value = FeatureState.Loading
        viewModelScope.launch {
            repository.getHomework()
                .onSuccess { _homeworkState.value = FeatureState.Success(it) }
                .onFailure { _homeworkState.value = FeatureState.Error(it.message ?: "Error fetching homework") }
        }
    }

    fun fetchAttendanceDetail(month: Int? = null, year: Int? = null) {
        _attendanceState.value = FeatureState.Loading
        viewModelScope.launch {
            try {
                repository.getAttendance(month, year)
                    .onSuccess { _attendanceState.value = FeatureState.Success(it) }
                    .onFailure { _attendanceState.value = FeatureState.Error(it.message ?: "Error fetching attendance") }
            } catch (e: Exception) {
                _attendanceState.value = FeatureState.Error(
                    e.message ?: "API method not yet registered — check ShikshashilaApi.kt"
                )
            }
        }
    }

    fun fetchFees() {
        _feesState.value = FeatureState.Loading
        viewModelScope.launch {
            try {
                repository.getFees()
                    .onSuccess { _feesState.value = FeatureState.Success(it) }
                    .onFailure { _feesState.value = FeatureState.Error(it.message ?: "Error fetching fees") }
            } catch (e: Exception) {
                _feesState.value = FeatureState.Error(
                    e.message ?: "API method not yet registered — check ShikshashilaApi.kt"
                )
            }
        }
    }

    fun fetchNotes() {
        _notesState.value = FeatureState.Loading
        viewModelScope.launch {
            try {
                repository.getNotes()
                    .onSuccess { _notesState.value = FeatureState.Success(it) }
                    .onFailure { _notesState.value = FeatureState.Error(it.message ?: "Error fetching notes") }
            } catch (e: Exception) {
                _notesState.value = FeatureState.Error(
                    e.message ?: "API method not yet registered — check ShikshashilaApi.kt"
                )
            }
        }
    }
}
