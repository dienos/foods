package com.jeongyookgak.jth.domain.repository.remote

import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo

interface ProductionsRepository {
    suspend fun getProductions() : List<ProductionsRepo>
}