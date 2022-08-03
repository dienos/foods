package com.jeongyookgak.jth.data.model

import com.jeongyookgak.jth.domain.model.remote.SampleRepo

data class SampleRepoRes(private val _name : String) : SampleRepo {
    override val name: String
        get() = _name
}