package com.shikshashila.app.ui.auth

import com.shikshashila.app.data.model.User

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}
