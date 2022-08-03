package com.jeongyookgak.jth.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.jeongyookgak.jth.data.db.converter.CategoryListConverter
import com.jeongyookgak.jth.data.db.converter.ProductionListConverter
import com.jeongyookgak.jth.data.db.dao.ProductionsDao
import com.jeongyookgak.jth.data.model.LocalCategoryItem
import com.jeongyookgak.jth.data.model.LocalProductionItem
import com.jeongyookgak.jth.data.model.LocalProductionsRepoRes

@Database(
    entities = [
        LocalProductionItem::class,
        LocalCategoryItem::class
    ],
    version = 1,
    exportSchema = false
)

abstract class JeongYookGakDataBase : RoomDatabase() {
    abstract fun productionsDao(): ProductionsDao
}