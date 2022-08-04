package com.jeongyookgak.jth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    private val _progress = MutableStateFlow(false)
    val progressFlow = _progress.asStateFlow()

    private val _toast = MutableStateFlow("")
    val toastFlow = _toast.asStateFlow()

    fun updateProgress(show : Boolean){
        _progress.value = show
    }

    fun updateToast(text : String){
        _toast.value = text
    }
}