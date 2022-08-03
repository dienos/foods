package com.jeongyookgak.jth.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeongyookgak.jth.data.db.dao.SampleDao
import com.jeongyookgak.jth.data.model.LocalSampleRepoRes

@Database(
    entities = [LocalSampleRepoRes::class],
    version = 1,
    exportSchema = false
)
abstract class SampleDataBae : RoomDatabase() {
   abstract fun SampleDao() : SampleDao
}