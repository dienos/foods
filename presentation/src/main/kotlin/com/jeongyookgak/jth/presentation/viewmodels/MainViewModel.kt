package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeongyookgak.jth.data.api.RESULT_OK
import com.jeongyookgak.jth.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.jeongyookgak.jth.domain.usecase.GetProductionsUseCase
import com.jeongyookgak.jth.presentation.R
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val getProductionsUseCase: GetProductionsUseCase
) : BaseViewModel(app) {

    val categoryData = MutableLiveData<CategoryData>()
    var tabData: ArrayList<String> = arrayListOf()

    fun setTabData() {
        tabData.add(app.getString(R.string.production))
        tabData.add(app.getString(R.string.like))
    }

    fun getCategories() {
        try {
            viewModelScope.launch {
                val response = getProductionsUseCase.invoke()

                if (response.code == RESULT_OK) {
                    categoryData.value = CategoryData(response.categories)
                }
            }
        } catch (e: Exception) {
            e.message?.let {
                updateToast(it)
            } ?: updateToast(app.getString(R.string.network_error))
        }
    }

}