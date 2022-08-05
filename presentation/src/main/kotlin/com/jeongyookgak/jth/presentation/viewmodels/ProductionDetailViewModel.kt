package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jeongyookgak.jth.domain.model.remote.Production
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductionDetailViewModel @Inject constructor(
    app: Application,
) : BaseViewModel(app) {
    val productionData = MutableLiveData<Production>()
}