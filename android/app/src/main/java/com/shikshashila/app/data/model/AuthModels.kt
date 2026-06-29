package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: Int,
    @SerializedName("school_id") val schoolId: String
)

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: User
)
