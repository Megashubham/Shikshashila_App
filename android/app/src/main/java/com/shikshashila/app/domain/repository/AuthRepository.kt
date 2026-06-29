package com.shikshashila.app.domain.repository

import com.shikshashila.app.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginId: String, password: String): Result<User>
    suspend fun logout()
    fun getSavedUser(): Flow<User?>
}
