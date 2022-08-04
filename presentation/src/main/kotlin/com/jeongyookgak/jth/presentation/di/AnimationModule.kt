package com.jeongyookgak.jth.presentation.di

import android.content.Context
import com.jeongyookgak.jth.presentation.util.AnimationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimationModule {

    @Provides
    @Singleton
    fun provideAnimationUtil(
        @ApplicationContext context: Context,
    ) : AnimationUtil = AnimationUtil(context)
}