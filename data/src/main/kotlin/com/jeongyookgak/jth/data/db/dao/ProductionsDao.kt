package com.jeongyookgak.jth.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jeongyookgak.jth.data.model.CategoryItem
import com.jeongyookgak.jth.data.model.LocalCategoryItem
import com.jeongyookgak.jth.data.model.LocalProductionItem
import com.jeongyookgak.jth.data.model.LocalProductionsRepoRes

@Dao
interface ProductionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<LocalProductionItem>)

    @Query("SELECT * FROM productions")
    suspend fun getProductions(): List<LocalProductionItem>

    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<LocalCategoryItem>
}