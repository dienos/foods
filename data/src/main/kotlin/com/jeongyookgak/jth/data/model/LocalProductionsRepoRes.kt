package com.jeongyookgak.jth.data.model

import androidx.room.ColumnInfo

data class LocalProductionsRepoRes(
    @ColumnInfo(name = "categories")
    var _categories: List<LocalCategoryItem>,

    @ColumnInfo(name = "productions")
    var _productions: List<LocalProductionItem>)