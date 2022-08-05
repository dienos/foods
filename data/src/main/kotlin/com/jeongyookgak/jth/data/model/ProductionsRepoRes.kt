package com.jeongyookgak.jth.data.model

import com.google.gson.annotations.SerializedName
import com.jeongyookgak.jth.domain.model.remote.Category
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo

data class ProductionsRepoRes(
    @SerializedName("categories")
    private val _categories: List<CategoryItem>,

    @SerializedName("productions")
    private val _productions: List<ProductionItem>,

    @SerializedName("error")
    private val error: ErrorResponse) : ProductionsRepo {

    override val categories: List<Category>
        get() = _categories

    override val productions: List<Production>
        get() = _productions
}