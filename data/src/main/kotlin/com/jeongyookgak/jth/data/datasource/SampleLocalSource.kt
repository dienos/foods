package com.jeongyookgak.jth.data.datasource

import com.jeongyookgak.jth.data.db.dao.SampleDao
import com.jeongyookgak.jth.data.model.LocalSampleRepoRes
import javax.inject.Inject

interface SampleLocalSource {
    suspend fun getLocalSimple(): List<LocalSampleRepoRes>
}

class SampleLocalSourceImpl @Inject constructor(
    private val dao: SampleDao
) : SampleLocalSource {
    override suspend fun getLocalSimple(): List<LocalSampleRepoRes> = dao.getSamples()
}