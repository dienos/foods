package com.jeongyookgak.jth.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JeongYookGakApplication : Application() {
    companion object {
        var favoriteList: ArrayList<String> = arrayListOf()
    }

    override fun onCreate() {
        super.onCreate()
    }
}