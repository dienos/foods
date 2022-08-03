package com.jeongyookgak.jth.data.api

import com.jeongyookgak.jth.data.model.SampleRepoRes
import retrofit2.http.GET

interface SampleService {
    @GET("sample")
    suspend fun getSample() : List<SampleRepoRes>
}