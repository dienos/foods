package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


open class BaseViewModel(val app: Application) : AndroidViewModel(app) {
    private val _progress = MutableStateFlow(false)
    val progressFlow = _progress.asStateFlow()

    private val _toast = MutableStateFlow("")
    val toastFlow = _toast.asStateFlow()

    fun updateProgress(show: Boolean) {
        _progress.value = show
    }

    fun updateToast(text: String) {
        _toast.value = text
    }
}