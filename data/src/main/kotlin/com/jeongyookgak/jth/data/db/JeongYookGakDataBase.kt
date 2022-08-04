package com.jeongyookgak.jth.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeongyookgak.jth.data.db.dao.ProductionsDao
import com.jeongyookgak.jth.data.model.LocalCategoryItem
import com.jeongyookgak.jth.data.model.LocalProductionItem

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