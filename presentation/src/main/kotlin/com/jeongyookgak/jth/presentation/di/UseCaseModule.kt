package com.jeongyookgak.jth.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.jeongyookgak.jth.domain.repository.local.LocalSampleRepository
import com.jeongyookgak.jth.domain.repository.remote.SampleRepository
import com.jeongyookgak.jth.domain.usecase.GetLocalSampleUseCase
import com.jeongyookgak.jth.domain.usecase.GetSampleUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun providesGetSampleUseCase(repository: SampleRepository): GetSampleUseCase {
        return GetSampleUseCase(repository)
    }

    @Provides
    fun providesGetLocalSampleUseCase(repository: LocalSampleRepository): GetLocalSampleUseCase {
        return GetLocalSampleUseCase(repository)
    }
}