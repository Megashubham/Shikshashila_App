package com.shikshashila.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shikshashila.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(loginId: String, password: String) {
        if (loginId.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Please enter both Login ID and Password")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = repository.login(loginId, password)
            result.onSuccess { user ->
                _authState.value = AuthState.Success(user)
            }.onFailure { error ->
                _authState.value = AuthState.Error(error.message ?: "An unknown error occurred")
            }
        }
    }
    
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
