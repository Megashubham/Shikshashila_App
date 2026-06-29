package com.shikshashila.app.data.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shikshashila.app.util.Constants

@Singleton
class AuthInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Don't add token for login endpoint
        if (originalRequest.url.encodedPath.contains("auth/login")) {
            return chain.proceed(originalRequest)
        }

        // Run synchronously to get the token for the interceptor
        val token = runBlocking {
            val preferences = dataStore.data.first()
            preferences[stringPreferencesKey(Constants.PREF_JWT_TOKEN)]
        }

        val requestBuilder = originalRequest.newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
