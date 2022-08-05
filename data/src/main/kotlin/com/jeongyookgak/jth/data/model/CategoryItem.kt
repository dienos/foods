package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Category

data class CategoryItem(
    @SerializedName("key")
    val _key: String,

    @SerializedName("name")
    val _name: String,

    @SerializedName("order")
    val _order: Int
) : Category {

    override val key: String
        get() = _key

    override val name: String
        get() = _name

    override val order: Int
        get() = _order
}