package com.jeongyookgak.jth.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.jeongyookgak.jth.data.datasource.ProductionLocalSource
import com.jeongyookgak.jth.data.datasource.LocalSourceImpl
import com.jeongyookgak.jth.data.datasource.ProductionsRemoteSource
import com.jeongyookgak.jth.data.datasource.ProductionsRemoteSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindProductionsRemoteSource(source: ProductionsRemoteSourceImpl): ProductionsRemoteSource

    @Singleton
    @Binds
    abstract fun bindSimpleLocalSource(source: LocalSourceImpl): ProductionLocalSource
}