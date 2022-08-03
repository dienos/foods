package com.jeongyookgak.jth.data.repository

import com.jeongyookgak.jth.data.datasource.SampleRemoteSource
import com.jeongyookgak.jth.domain.model.remote.SampleRepo
import com.jeongyookgak.jth.domain.repository.remote.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val remoteSource: SampleRemoteSource,
) : SampleRepository {
    override suspend fun getSample(): List<SampleRepo> = remoteSource.getSimple()
}