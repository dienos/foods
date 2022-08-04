package com.jeongyookgak.jth.presentation.di

import android.content.Context
import com.jeongyookgak.jth.presentation.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkCheckModule {

    @Provides
    @Singleton
    fun provideNetworkUtil(
        @ApplicationContext context: Context
    ) : NetworkUtil = NetworkUtil(context)
}