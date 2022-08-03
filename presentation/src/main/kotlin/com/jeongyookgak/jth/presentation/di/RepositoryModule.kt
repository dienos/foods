package com.jeongyookgak.jth.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.jeongyookgak.jth.data.repository.LocalSampleRepositoryImpl
import com.jeongyookgak.jth.data.repository.SampleRepositoryImpl
import com.jeongyookgak.jth.domain.repository.local.LocalSampleRepository
import com.jeongyookgak.jth.domain.repository.remote.SampleRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSampleRepository(repository: SampleRepositoryImpl): SampleRepository

    @Singleton
    @Binds
    abstract fun bindLocalSampleRepository(repository: LocalSampleRepositoryImpl): LocalSampleRepository
}