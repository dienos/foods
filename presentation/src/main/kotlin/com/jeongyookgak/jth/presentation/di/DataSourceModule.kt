package com.jeongyookgak.jth.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.jeongyookgak.jth.data.datasource.SampleLocalSource
import com.jeongyookgak.jth.data.datasource.SampleLocalSourceImpl
import com.jeongyookgak.jth.data.datasource.SampleRemoteSource
import com.jeongyookgak.jth.data.datasource.SampleRemoteSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindSimpleRemoteSource(source: SampleRemoteSourceImpl): SampleRemoteSource

    @Singleton
    @Binds
    abstract fun bindSimpleLocalSource(source: SampleLocalSourceImpl): SampleLocalSource
}