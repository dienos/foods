package com.jeongyookgak.jth.domain.usecase

import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo
import com.jeongyookgak.jth.domain.repository.remote.ProductionsRepository

class GetProductionsUseCase(private val repository: ProductionsRepository) {
    suspend operator fun invoke(
    ):  ProductionsRepo {
        return repository.getProductions()
    }
}