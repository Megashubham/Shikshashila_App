package com.shikshashila.app.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?
) {
    val isSuccess: Boolean
        get() = status == "success"
}
