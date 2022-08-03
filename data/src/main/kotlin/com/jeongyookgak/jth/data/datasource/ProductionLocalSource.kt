package com.jeongyookgak.jth.data.datasource

import com.jeongyookgak.jth.data.db.dao.ProductionsDao
import com.jeongyookgak.jth.data.model.LocalProductionItem
import javax.inject.Inject

interface ProductionLocalSource {
    suspend fun getProductions(): List<LocalProductionItem>
}

class LocalSourceImpl @Inject constructor(
    private val dao: ProductionsDao
) : ProductionLocalSource {
    override suspend fun getProductions(): List<LocalProductionItem> = dao.getProductions()
}