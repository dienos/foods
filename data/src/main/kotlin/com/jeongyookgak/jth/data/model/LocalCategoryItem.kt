package com.jeongyookgak.jth.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class LocalCategoryItem(
    @PrimaryKey
    @ColumnInfo(name = "category_key")
    var key: String,

    @ColumnInfo(name = "name")
    var name: String,
)