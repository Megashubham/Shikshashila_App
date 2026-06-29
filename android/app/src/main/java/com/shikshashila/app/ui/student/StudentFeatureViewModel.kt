package com.shikshashila.app.ui.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.data.model.HomeworkResponse
import com.shikshashila.app.data.model.ResultsResponse
import com.shikshashila.app.data.model.RoutineResponse
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

    private val _routineState = MutableStateFlow<FeatureState<RoutineResponse>>(FeatureState.Idle)
    val routineState: StateFlow<FeatureState<RoutineResponse>> = _routineState.asStateFlow()

    private val _resultsState = MutableStateFlow<FeatureState<ResultsResponse>>(FeatureState.Idle)
    val resultsState: StateFlow<FeatureState<ResultsResponse>> = _resultsState.asStateFlow()

    private val _homeworkState = MutableStateFlow<FeatureState<HomeworkResponse>>(FeatureState.Idle)
    val homeworkState: StateFlow<FeatureState<HomeworkResponse>> = _homeworkState.asStateFlow()

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
}
