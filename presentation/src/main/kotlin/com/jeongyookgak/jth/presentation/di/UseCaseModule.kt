package com.jeongyookgak.jth.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.jeongyookgak.jth.domain.repository.local.LocalProductionRepository
import com.jeongyookgak.jth.domain.repository.remote.ProductionsRepository
import com.jeongyookgak.jth.domain.usecase.GetLocalSampleUseCase
import com.jeongyookgak.jth.domain.usecase.GetProductionsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesGetSampleUseCase(repository: ProductionsRepository): GetProductionsUseCase {
        return GetProductionsUseCase(repository)
    }

    @Provides
    fun providesGetLocalSampleUseCase(repository: LocalProductionRepository): GetLocalSampleUseCase {
        return GetLocalSampleUseCase(repository)
    }
}