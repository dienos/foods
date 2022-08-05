package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Production

data class ProductionItem(
    @SerializedName("key")
    val _key: String,

    @SerializedName("category_key")
    val _categoryKey: String,

    @SerializedName("name")
    val _name: String,

    @SerializedName("price")
    val _price: String,

    @SerializedName("thumbnail")
    val _thumbnail: String,

    @SerializedName("order")
    val _order: Int,

    var _isFavorite: Boolean = false
) : Production {
    override var isFavorite: Boolean
        get() = _isFavorite
        set(value) {
            _isFavorite = value
        }

    override val categoryKey: String
        get() = _categoryKey

    override val key: String
        get() = _key

    override val name: String
        get() = _name

    override val price: String
        get() = _price

    override val order: Int
        get() = _order

    override val thumbnail: String
        get() = _thumbnail
}