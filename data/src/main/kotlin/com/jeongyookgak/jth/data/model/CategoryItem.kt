package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Category

data class CategoryItem(
    @SerializedName("key")
    val _key: String,

    @SerializedName("name")
    val _name: String
) : Category {

    override val key: String
        get() = _key

    override val name: String
        get() = _name
}