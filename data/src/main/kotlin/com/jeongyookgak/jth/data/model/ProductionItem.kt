package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Production

data class ProductionItem(
    @SerializedName("categoryKey")
    val _categoryKey: String,

    @SerializedName("key")
    val _key: String,

    @SerializedName("name")
    val _name: String,

    @SerializedName("price")
    val _price: String
) : Production {
    override val categoryKey: String
        get() = _categoryKey

    override val key: String
        get() = _key

    override val name: String
        get() = _name

    override val price: String
        get() = _price
}