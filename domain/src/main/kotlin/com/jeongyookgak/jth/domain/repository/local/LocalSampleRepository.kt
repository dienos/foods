package com.jeongyookgak.jth.domain.repository.local

import com.jeongyookgak.jth.domain.model.local.LocalSampleRepo

interface LocalSampleRepository {
    suspend fun getLocalSample() : List<LocalSampleRepo>
}