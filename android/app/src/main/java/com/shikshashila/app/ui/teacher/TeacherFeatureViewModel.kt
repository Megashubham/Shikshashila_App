package com.shikshashila.app.ui.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.*
import com.shikshashila.app.domain.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TeacherFeatureState<out T> {
    object Idle : TeacherFeatureState<Nothing>()
    object Loading : TeacherFeatureState<Nothing>()
    data class Success<T>(val data: T) : TeacherFeatureState<T>()
    data class Error(val message: String) : TeacherFeatureState<Nothing>()
}

@HiltViewModel
class TeacherFeatureViewModel @Inject constructor(
    private val repository: TeacherRepository
) : ViewModel() {

    // ── Classes ───────────────────────────────────────────────────────────────
    private val _classesState = MutableStateFlow<TeacherFeatureState<TeacherClassesResponse>>(TeacherFeatureState.Idle)
    val classesState: StateFlow<TeacherFeatureState<TeacherClassesResponse>> = _classesState.asStateFlow()

    // ── Attendance ────────────────────────────────────────────────────────────
    private val _attendanceState = MutableStateFlow<TeacherFeatureState<TeacherAttendanceResponse>>(TeacherFeatureState.Idle)
    val attendanceState: StateFlow<TeacherFeatureState<TeacherAttendanceResponse>> = _attendanceState.asStateFlow()

    // ── Assignments ───────────────────────────────────────────────────────────
    private val _assignmentsState = MutableStateFlow<TeacherFeatureState<TeacherAssignmentsResponse>>(TeacherFeatureState.Idle)
    val assignmentsState: StateFlow<TeacherFeatureState<TeacherAssignmentsResponse>> = _assignmentsState.asStateFlow()

    // ── Submit Attendance ─────────────────────────────────────────────────────
    private val _submitState = MutableStateFlow<TeacherFeatureState<String>>(TeacherFeatureState.Idle)
    val submitState: StateFlow<TeacherFeatureState<String>> = _submitState.asStateFlow()

    // ── Routine (Timetable) ───────────────────────────────────────────────────
    private val _routineState = MutableStateFlow<TeacherFeatureState<TeacherRoutineResponse>>(TeacherFeatureState.Idle)
    val routineState: StateFlow<TeacherFeatureState<TeacherRoutineResponse>> = _routineState.asStateFlow()

    // ── Students List ─────────────────────────────────────────────────────────
    private val _studentsListState = MutableStateFlow<TeacherFeatureState<TeacherStudentsListResponse>>(TeacherFeatureState.Idle)
    val studentsListState: StateFlow<TeacherFeatureState<TeacherStudentsListResponse>> = _studentsListState.asStateFlow()

    // ── Fetch methods ─────────────────────────────────────────────────────────

    fun fetchClasses() {
        _classesState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            repository.getClasses()
                .onSuccess { _classesState.value = TeacherFeatureState.Success(it) }
                .onFailure { _classesState.value = TeacherFeatureState.Error(it.message ?: "Error fetching classes") }
        }
    }

    fun fetchAttendance(classId: String, sectionId: String, date: String) {
        _attendanceState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            repository.getAttendance(classId, sectionId, date)
                .onSuccess { _attendanceState.value = TeacherFeatureState.Success(it) }
                .onFailure { _attendanceState.value = TeacherFeatureState.Error(it.message ?: "Error fetching students") }
        }
    }

    fun submitAttendance(request: SubmitAttendanceRequest) {
        _submitState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            repository.submitAttendance(request)
                .onSuccess { _submitState.value = TeacherFeatureState.Success(it) }
                .onFailure { _submitState.value = TeacherFeatureState.Error(it.message ?: "Error submitting attendance") }
        }
    }

    fun resetSubmitState() {
        _submitState.value = TeacherFeatureState.Idle
    }

    fun fetchAssignments(classId: String? = null, sectionId: String? = null) {
        _assignmentsState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            repository.getAssignments(classId, sectionId)
                .onSuccess { _assignmentsState.value = TeacherFeatureState.Success(it) }
                .onFailure { _assignmentsState.value = TeacherFeatureState.Error(it.message ?: "Error fetching assignments") }
        }
    }

    fun fetchTeacherRoutine() {
        _routineState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            try {
                repository.getTeacherRoutine()
                    .onSuccess { _routineState.value = TeacherFeatureState.Success(it) }
                    .onFailure { _routineState.value = TeacherFeatureState.Error(it.message ?: "Error fetching routine") }
            } catch (e: Exception) {
                _routineState.value = TeacherFeatureState.Error(
                    e.message ?: "API method not yet registered — check ShikshashilaApi.kt"
                )
            }
        }
    }

    fun fetchStudentsList(classId: String, sectionId: String) {
        _studentsListState.value = TeacherFeatureState.Loading
        viewModelScope.launch {
            try {
                repository.getStudentsList(classId, sectionId)
                    .onSuccess { _studentsListState.value = TeacherFeatureState.Success(it) }
                    .onFailure { _studentsListState.value = TeacherFeatureState.Error(it.message ?: "Error fetching students") }
            } catch (e: Exception) {
                _studentsListState.value = TeacherFeatureState.Error(
                    e.message ?: "API method not yet registered — check ShikshashilaApi.kt"
                )
            }
        }
    }
}
