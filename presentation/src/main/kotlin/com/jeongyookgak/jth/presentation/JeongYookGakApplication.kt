package com.jeongyookgak.jth.presentation

import android.app.Application
import android.content.Context
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.di.PreferencesUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JeongYookGakApplication : Application() {
    companion object {
        var favoriteList: ArrayList<String> = arrayListOf()

        fun controlFavoriteList(context: Context, isChecked : Boolean, data : Production) {
            if (isChecked) {
                if(favoriteList.isNotEmpty()) {
                    favoriteList.remove(data.key)
                }

                favoriteList.add(data.key)
            } else {
                if(favoriteList.isNotEmpty()) {
                    favoriteList.remove(data.key)
                }
            }

            PreferencesUtil.setStringArrayPref(context, favoriteList)
        }

        fun setSearchWord(context : Context?, value : String) {
            PreferencesUtil.setSearchText(context, value)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}