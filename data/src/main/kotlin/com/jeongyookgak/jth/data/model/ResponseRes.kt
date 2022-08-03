package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Response

data class ResponseRes(
    @SerializedName("code")
    val _code: String
) : Response {
    override val code: String
        get() = _code
}