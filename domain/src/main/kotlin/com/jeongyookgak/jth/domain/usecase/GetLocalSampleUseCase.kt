package com.jeongyookgak.jth.domain.usecase

import com.jeongyookgak.jth.domain.model.local.LocalSampleRepo
import com.jeongyookgak.jth.domain.repository.local.LocalSampleRepository

class GetLocalSampleUseCase(private val repository: LocalSampleRepository) {
    suspend operator fun invoke(
    ): List<LocalSampleRepo> {
        return repository.getLocalSample()
    }
}