package com.jeongyookgak.jth.domain.usecase

import com.jeongyookgak.jth.domain.model.remote.SampleRepo
import com.jeongyookgak.jth.domain.repository.remote.SampleRepository

class GetSampleUseCase(private val repository: SampleRepository) {
    suspend operator fun invoke(
    ): List<SampleRepo> {
        return repository.getSample()
    }
}