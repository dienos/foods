package com.jeongyookgak.jth.domain.usecase

import com.jeongyookgak.jth.domain.model.local.LocalProduction
import com.jeongyookgak.jth.domain.repository.local.LocalProductionRepository

class GetLocalSampleUseCase(private val repository: LocalProductionRepository) {
    suspend operator fun invoke(
    ): List<LocalProduction> {
        return repository.getLocalProductions()
    }
}