package com.jeongyookgak.jth.data.api

import com.jeongyookgak.jth.data.model.ProductionsRepoRes
import retrofit2.http.GET

interface ProductionService {
    @GET(PRODUCTION_LIST_URL)
    suspend fun getProductions() : List<ProductionsRepoRes>
}