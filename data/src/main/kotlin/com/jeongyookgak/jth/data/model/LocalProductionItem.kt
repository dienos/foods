package com.jeongyookgak.jth.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productions")
data class LocalProductionItem(
    @PrimaryKey
    @ColumnInfo(name = "key")
    var key: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)