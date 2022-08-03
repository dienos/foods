package com.jeongyookgak.jth.data.datasource

import com.jeongyookgak.jth.data.api.SampleService
import com.jeongyookgak.jth.data.model.SampleRepoRes
import javax.inject.Inject

interface SampleRemoteSource {
    suspend fun getSimple(): List<SampleRepoRes>
}

class SampleRemoteSourceImpl @Inject constructor(
    private val sampleService: SampleService
) : SampleRemoteSource {
    override suspend fun getSimple(): List<SampleRepoRes> = sampleService.getSample()
}