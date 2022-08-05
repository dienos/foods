package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("header")
    val header: String,

    @SerializedName("message")
    val message: String
)