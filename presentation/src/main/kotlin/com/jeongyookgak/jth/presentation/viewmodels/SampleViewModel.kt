package com.jeongyookgak.jth.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.jeongyookgak.jth.domain.model.local.LocalSampleRepo
import com.jeongyookgak.jth.domain.model.remote.SampleRepo
import com.jeongyookgak.jth.domain.usecase.GetLocalSampleUseCase
import com.jeongyookgak.jth.domain.usecase.GetSampleUseCase
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getSampleUseCase: GetSampleUseCase,
    private val getLocalSampleUseCase: GetLocalSampleUseCase,
) : BaseViewModel() {

    private var _sampleData = MutableLiveData<List<SampleRepo>>()
    val sampleRepository : LiveData<List<SampleRepo>> = _sampleData

    private var _sampleLocalData = MutableLiveData<List<LocalSampleRepo>>()
    val sampleLocalRepository : LiveData<List<LocalSampleRepo>> = _sampleLocalData

    fun getSimpleData() {
        viewModelScope.launch {
            _sampleData.value = getSampleUseCase.invoke()
        }
    }

    fun getLocalSimpleData() {
        viewModelScope.launch {
            _sampleLocalData.value = getLocalSampleUseCase.invoke()
        }
    }
}