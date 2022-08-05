package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeongyookgak.jth.data.model.CategoryData
import com.jeongyookgak.jth.data.model.ProductionData
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.domain.usecase.GetProductionsUseCase
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.di.PreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductionViewModel @Inject constructor(
    app: Application,
    private val getProductionsUseCase: GetProductionsUseCase
) : BaseViewModel(app) {
    private var productionDataByFiller: ArrayList<Production> = arrayListOf()
    val productionData = MutableLiveData<ProductionData>()
    val categoryData = MutableLiveData<CategoryData>()

    private fun joinFavoriteData(
        remoteData: ArrayList<Production>,
        localFavoriteData: ArrayList<String>?
    ): List<Production> {
        val result = arrayListOf<Production>()
        result.addAll(remoteData)

        result.forEachIndexed { index, production ->
            localFavoriteData?.forEach { localKey ->
                if (production.key == localKey) {
                    result[index].isFavorite = true
                }
            }
        }

        return result
    }

    fun getProductionsByCategory(key: String) {
        val list = productionDataByFiller.filter {
            it.categoryKey == key
        }

        productionData.value = ProductionData(
            joinFavoriteData(
                list as ArrayList<Production>,
                PreferencesUtil.getStringArrayPref(app) as ArrayList<String>
            )
        )
    }

    fun getProductions() {
        updateProgress(true)

        viewModelScope.launch {
            try {
                val response = getProductionsUseCase.invoke()

                categoryData.value = CategoryData(response.categories)

                productionDataByFiller.clear()
                productionDataByFiller.addAll(response.productions)

                PreferencesUtil.getStringArrayPref(app)?.let {
                    productionData.value = ProductionData(
                        joinFavoriteData(
                            response.productions as ArrayList<Production>,
                            PreferencesUtil.getStringArrayPref(app) as ArrayList<String>
                        )
                    )
                }?: run {
                    productionData.value = ProductionData(response.productions)
                }

                updateProgress(false)
            } catch (e: Exception) {
                e.message?.let {
                    updateProgress(false)
                    updateToast(it)
                } ?: updateToast(app.getString(R.string.network_error))
            }
        }
    }
}