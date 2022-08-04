package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel(app : Application) : BaseViewModel(app) {
    private val _complete = MutableStateFlow(false)
    val complete = _complete.asStateFlow()

    fun updateComplete(){
        _complete.value = true
    }
}