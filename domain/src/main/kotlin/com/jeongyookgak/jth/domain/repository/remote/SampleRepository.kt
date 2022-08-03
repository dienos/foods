package com.jeongyookgak.jth.domain.repository.remote

import com.jeongyookgak.jth.domain.model.remote.SampleRepo

interface SampleRepository {
    suspend fun getSample() : List<SampleRepo>
}