package com.jeongyookgak.jth.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeongyookgak.jth.domain.model.local.LocalProduction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo
import com.jeongyookgak.jth.domain.usecase.GetLocalSampleUseCase
import com.jeongyookgak.jth.domain.usecase.GetProductionsUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductionsUseCase: GetProductionsUseCase,
    private val getLocalProductionUseCase: GetLocalSampleUseCase,
) : BaseViewModel() {

    var ProductionsData = MutableLiveData<ProductionsRepo>()

    private var _productionLocalData = MutableLiveData<List<LocalProduction>>()

    fun getProductions() {
        viewModelScope.launch {
            ProductionsData.value = getProductionsUseCase.invoke()
        }
    }

    fun getLocalSimpleData() {
        viewModelScope.launch {
            _productionLocalData.value = getLocalProductionUseCase.invoke()
        }
    }
}