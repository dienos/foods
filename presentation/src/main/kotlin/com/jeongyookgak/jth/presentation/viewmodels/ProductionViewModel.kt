package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeongyookgak.jth.data.api.RESULT_OK
import com.jeongyookgak.jth.data.model.CategoryData
import com.jeongyookgak.jth.data.model.ProductionData
import com.jeongyookgak.jth.data.model.ProductionItem
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
    var productionDataByFiller : List<Production> = arrayListOf()
    val productionData = MutableLiveData<ProductionData>()
    val categoryData = MutableLiveData<CategoryData>()

    private fun joinFavoriteData(
        remoteData: List<Production>,
        localFavoriteData: List<String>?
    ): List<Production> {
        val result: List<Production> = remoteData

        result.forEachIndexed { i, data ->
            val key = data.key

            localFavoriteData?.forEach { favoriteData ->

                if (key == favoriteData) {
                    if (result is ArrayList) {
                        result[i] = ProductionItem(
                            _key = key,
                            _categoryKey = data.categoryKey,
                            _name = data.name,
                            _price = data.price,
                            _isFavorite = true
                        )
                    }
                }
            }
        }

        return result
    }

    fun getProductionsByCategory(key : String) {
        val list = productionDataByFiller.filter {
            it.categoryKey == key
        }

        productionData.value = ProductionData(list)
    }

    fun getProductions() {
        try {
            viewModelScope.launch {
                val response = getProductionsUseCase.invoke()

                if (response.code == RESULT_OK) {
                    categoryData.value = CategoryData(response.categories)

                    productionData.value = ProductionData(
                        joinFavoriteData(
                            response.productions,
                            PreferencesUtil.getStringArrayPref(app)
                        )
                    )

                    productionData.value?.list?.let {
                        productionDataByFiller = it
                    }

                }
            }
        } catch (e: Exception) {
            e.message?.let {
                updateToast(it)
            } ?: updateToast(app.getString(R.string.network_error))
        }
    }
}