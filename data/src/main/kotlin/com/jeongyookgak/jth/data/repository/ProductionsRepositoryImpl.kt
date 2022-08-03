package com.jeongyookgak.jth.data.repository

import com.jeongyookgak.jth.data.datasource.ProductionsRemoteSource
import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo
import com.jeongyookgak.jth.domain.repository.remote.ProductionsRepository
import javax.inject.Inject

class ProductionsRepositoryImpl @Inject constructor(
    private val remoteSource: ProductionsRemoteSource,
) : ProductionsRepository {
    override suspend fun getProductions(): List<ProductionsRepo> = remoteSource.getProductions()
}