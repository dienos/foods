package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo
import com.jeongyookgak.jth.domain.model.remote.Response

data class ProductionsRepoRes(
    private val _code : String,

    @SerializedName("categories")
    private val _categories: List<CategoryItem>,

    @SerializedName("productions")
    private val _productions: List<ProductionItem>,) : ProductionsRepo {

    override val categories: List<Category>
        get() = _categories

    override val code: String
        get() = _code

    override val productions: List<Production>
        get() = _productions
}