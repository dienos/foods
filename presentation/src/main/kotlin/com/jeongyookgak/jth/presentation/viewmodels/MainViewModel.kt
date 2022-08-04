package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import com.jeongyookgak.jth.presentation.R
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
) : BaseViewModel(app) {

    var tabData: ArrayList<String> = arrayListOf()

    fun setTabData() {
        tabData.add(app.getString(R.string.production))
        tabData.add(app.getString(R.string.like))
    }
}