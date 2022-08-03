package com.jeongyookgak.jth.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.jeongyookgak.jth.data.db.JeongYookGakDataBase
import com.jeongyookgak.jth.data.db.converter.CategoryListConverter
import com.jeongyookgak.jth.data.db.converter.ProductionListConverter
import com.jeongyookgak.jth.data.db.dao.ProductionsDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    private const val name = "productions.db"

    @Provides
    @Singleton
    fun provideJeongYookGakDatabase(
        @ApplicationContext context: Context
    ): JeongYookGakDataBase = Room
        .databaseBuilder(context, JeongYookGakDataBase::class.java, name)
        .build()

    @Provides
    @Singleton
    fun provideProductionsDao(dataBase: JeongYookGakDataBase): ProductionsDao {
        return dataBase.productionsDao()
    }
}



