package com.jeongyookgak.jth.data.repository

import com.jeongyookgak.jth.data.datasource.SampleLocalSource
import com.jeongyookgak.jth.domain.model.local.LocalSampleRepo
import com.jeongyookgak.jth.domain.repository.local.LocalSampleRepository
import javax.inject.Inject

class LocalSampleRepositoryImpl @Inject constructor(
    private val localSource: SampleLocalSource,
) : LocalSampleRepository {
    override suspend fun getLocalSample(): List<LocalSampleRepo> =  localSource.getLocalSimple()
}