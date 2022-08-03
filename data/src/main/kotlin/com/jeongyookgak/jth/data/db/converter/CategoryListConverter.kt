package com.jeongyookgak.jth.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jeongyookgak.jth.data.model.LocalCategoryItem

@ProvidedTypeConverter
class CategoryListConverter {
    @TypeConverter
    fun listToJson(value: List<LocalCategoryItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<LocalCategoryItem>? {
        return Gson().fromJson(value,Array<LocalCategoryItem>::class.java)?.toList()
    }
}