package com.jeongyookgak.jth.data.repository

import com.jeongyookgak.jth.data.datasource.ProductionLocalSource
import com.jeongyookgak.jth.data.model.LocalProductionItem
import com.jeongyookgak.jth.domain.repository.local.LocalProductionRepository
import javax.inject.Inject

class LocalProductionRepositoryImpl @Inject constructor(
    private val localSource: ProductionLocalSource,
) : LocalProductionRepository {
    override suspend fun getLocalProductions(): List<LocalProductionItem> =  localSource.getProductions()
}