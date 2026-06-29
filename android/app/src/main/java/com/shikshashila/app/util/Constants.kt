package com.shikshashila.app.util

object Constants {
    // Points to the new API layer we created
    // Use 10.0.2.2 for Android emulator to access localhost, or your actual IP for physical device testing
    // Change to https if the server supports it and emulator has correct DNS/Time
    const val BASE_URL = "https://erp.shikshashila.com/api/"
    const val DATASTORE_PREF_NAME = "shikshashila_preferences"
    const val PREF_JWT_TOKEN = "jwt_token"
}
