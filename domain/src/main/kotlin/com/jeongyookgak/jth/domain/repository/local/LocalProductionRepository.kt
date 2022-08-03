package com.jeongyookgak.jth.domain.repository.local

import com.jeongyookgak.jth.domain.model.local.LocalProduction

interface LocalProductionRepository {
    suspend fun getLocalProductions() : List<LocalProduction>
}