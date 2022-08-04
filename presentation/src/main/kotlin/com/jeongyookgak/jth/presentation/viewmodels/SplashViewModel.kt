package com.jeongyookgak.jth.presentation.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel : BaseViewModel() {
    private val _complete = MutableStateFlow(false)
    val complete = _complete.asStateFlow()

    fun updateComplete(){
        _complete.value = true
    }
}