package com.jeongyookgak.jth.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jeongyookgak.jth.data.model.LocalProductionItem

@ProvidedTypeConverter
class ProductionListConverter {
    @TypeConverter
    fun listToJson(value: List<LocalProductionItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<LocalProductionItem>? {
        return Gson().fromJson(value,Array<LocalProductionItem>::class.java)?.toList()
    }
}