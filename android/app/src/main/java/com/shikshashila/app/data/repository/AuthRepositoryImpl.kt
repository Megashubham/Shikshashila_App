package com.shikshashila.app.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.shikshashila.app.data.api.ShikshashilaApi
import com.shikshashila.app.data.model.User
import com.shikshashila.app.domain.repository.AuthRepository
import com.shikshashila.app.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: ShikshashilaApi,
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) : AuthRepository {

    private val jwtKey = stringPreferencesKey(Constants.PREF_JWT_TOKEN)
    private val userKey = stringPreferencesKey("saved_user")

    override suspend fun login(loginId: String, password: String): Result<User> {
        return try {
            val jsonObject = JSONObject().apply {
                put("login_id", loginId)
                put("password", password)
            }
            val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
            
            val response = api.login(requestBody)
            val body = response.body()

            if (response.isSuccessful && body != null && body.isSuccess) {
                val loginData = body.data
                if (loginData != null) {
                    // Save JWT Token and User data locally
                    dataStore.edit { preferences ->
                        preferences[jwtKey] = loginData.token
                        preferences[userKey] = gson.toJson(loginData.user)
                    }
                    Result.success(loginData.user)
                } else {
                    Result.failure(Exception("Login data missing in response"))
                }
            } else {
                val errorMsg = body?.message ?: "Login failed. Please check credentials."
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(jwtKey)
            preferences.remove(userKey)
        }
    }

    override fun getSavedUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val userStr = preferences[userKey]
            if (userStr != null) {
                try {
                    gson.fromJson(userStr, User::class.java)
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }
        }
    }
}
