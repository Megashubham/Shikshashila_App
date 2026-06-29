package com.shikshashila.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.api.ShikshashilaApi
import com.shikshashila.app.data.model.AdminAttendanceResponse
import com.shikshashila.app.data.model.AdminClassesResponse
import com.shikshashila.app.data.model.AdminStaffResponse
import com.shikshashila.app.data.model.AdminStudentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Sealed state ────────────────────────────────────────────────────────────

sealed class AdminFeatureState<out T> {
    object Idle : AdminFeatureState<Nothing>()
    object Loading : AdminFeatureState<Nothing>()
    data class Success<T>(val data: T) : AdminFeatureState<T>()
    data class Error(val message: String) : AdminFeatureState<Nothing>()
}

// ─── ViewModel ───────────────────────────────────────────────────────────────

@HiltViewModel
class AdminFeatureViewModel @Inject constructor(
    private val api: ShikshashilaApi
) : ViewModel() {

    // Students
    private val _studentsState =
        MutableStateFlow<AdminFeatureState<AdminStudentsResponse>>(AdminFeatureState.Idle)
    val studentsState = _studentsState.asStateFlow()

    // Staff
    private val _staffState =
        MutableStateFlow<AdminFeatureState<AdminStaffResponse>>(AdminFeatureState.Idle)
    val staffState = _staffState.asStateFlow()

    // Attendance
    private val _attendanceState =
        MutableStateFlow<AdminFeatureState<AdminAttendanceResponse>>(AdminFeatureState.Idle)
    val attendanceState = _attendanceState.asStateFlow()

    // Classes
    private val _classesState =
        MutableStateFlow<AdminFeatureState<AdminClassesResponse>>(AdminFeatureState.Idle)
    val classesState = _classesState.asStateFlow()

    // ── Fetch functions ──────────────────────────────────────────────────────

    fun fetchStudents(search: String = "", classId: String? = null) {
        _studentsState.value = AdminFeatureState.Loading
        viewModelScope.launch {
            try {
                val resp = api.getAdminStudents(search.ifEmpty { null }, classId)
                if (resp.isSuccessful && resp.body()?.success == true) {
                    _studentsState.value = AdminFeatureState.Success(resp.body()!!.data!!)
                } else {
                    _studentsState.value = AdminFeatureState.Error(
                        resp.body()?.message ?: "Failed to load students"
                    )
                }
            } catch (e: Exception) {
                _studentsState.value = AdminFeatureState.Error(e.message ?: "Network error")
            }
        }
    }

    fun fetchStaff(type: String? = null) {
        _staffState.value = AdminFeatureState.Loading
        viewModelScope.launch {
            try {
                val resp = api.getAdminStaff(type)
                if (resp.isSuccessful && resp.body()?.success == true) {
                    _staffState.value = AdminFeatureState.Success(resp.body()!!.data!!)
                } else {
                    _staffState.value = AdminFeatureState.Error(
                        resp.body()?.message ?: "Failed to load staff"
                    )
                }
            } catch (e: Exception) {
                _staffState.value = AdminFeatureState.Error(e.message ?: "Network error")
            }
        }
    }

    fun fetchAttendance(date: String? = null) {
        _attendanceState.value = AdminFeatureState.Loading
        viewModelScope.launch {
            try {
                val resp = api.getAdminAttendance(date)
                if (resp.isSuccessful && resp.body()?.success == true) {
                    _attendanceState.value = AdminFeatureState.Success(resp.body()!!.data!!)
                } else {
                    _attendanceState.value = AdminFeatureState.Error(
                        resp.body()?.message ?: "Failed to load attendance"
                    )
                }
            } catch (e: Exception) {
                _attendanceState.value = AdminFeatureState.Error(e.message ?: "Network error")
            }
        }
    }

    fun fetchClasses() {
        _classesState.value = AdminFeatureState.Loading
        viewModelScope.launch {
            try {
                val resp = api.getAdminClasses()
                if (resp.isSuccessful && resp.body()?.success == true) {
                    _classesState.value = AdminFeatureState.Success(resp.body()!!.data!!)
                } else {
                    _classesState.value = AdminFeatureState.Error(
                        resp.body()?.message ?: "Failed to load classes"
                    )
                }
            } catch (e: Exception) {
                _classesState.value = AdminFeatureState.Error(e.message ?: "Network error")
            }
        }
    }

    // ── Reset helpers (allow re-fetch after navigating away) ─────────────────

    fun resetStudents() { _studentsState.value = AdminFeatureState.Idle }
    fun resetStaff()    { _staffState.value = AdminFeatureState.Idle }
    fun resetAttendance() { _attendanceState.value = AdminFeatureState.Idle }
    fun resetClasses()  { _classesState.value = AdminFeatureState.Idle }
}
