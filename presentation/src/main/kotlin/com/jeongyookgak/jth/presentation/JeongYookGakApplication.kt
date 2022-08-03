package com.jeongyookgak.jth.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JeongYookGakApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}