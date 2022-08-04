package com.jeongyookgak.jth.data.datasource

import com.jeongyookgak.jth.data.api.ProductionService
import com.jeongyookgak.jth.data.model.ProductionsRepoRes
import javax.inject.Inject

interface ProductionsRemoteSource {
    suspend fun getProductions(): ProductionsRepoRes
}

class ProductionsRemoteSourceImpl @Inject constructor(
    private val service: ProductionService
) : ProductionsRemoteSource {
    override suspend fun getProductions(): ProductionsRepoRes = service.getProductions()
}