package com.jeongyookgak.jth.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeongyookgak.jth.domain.model.local.LocalProduction

@Entity(tableName = "productions")
data class LocalProductionItem(
    @PrimaryKey
    @ColumnInfo(name = "key")
    var key: String,

    @ColumnInfo(name = "categoryKey")
    var categoryKey: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "price")
    var price: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) : LocalProduction {
    override val keyValue: String
        get() = key
    override val categoryKeyValue: String
        get() = categoryKey
    override val nameValue: String
        get() = name
    override val priceValue: String
        get() = price
    override var isFavoriteValue: Boolean
        get() = isFavorite
        set(value) {
            isFavorite = value
        }
}