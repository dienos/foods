package com.jeongyookgak.jth.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.jeongyookgak.jth.data.repository.ProductionsRepositoryImpl
import com.jeongyookgak.jth.domain.repository.remote.ProductionsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProductionsRepository(repository: ProductionsRepositoryImpl): ProductionsRepository
}