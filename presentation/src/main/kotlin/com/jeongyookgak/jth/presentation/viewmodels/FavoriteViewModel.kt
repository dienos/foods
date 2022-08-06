package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class FavoriteViewModel @Inject constructor(
    app: Application,
    private val getProductionsUseCase: GetProductionsUseCase
) : BaseViewModel(app) {
    val favoriteData = MutableLiveData<ProductionData>()
    private var productionDataByFiller: List<Production> = arrayListOf()

    private fun getOnlyFavoriteData(
        remoteData: ArrayList<Production>,
        localFavoriteData: ArrayList<String>?
    ): List<Production> {
        return joinFavoriteData(remoteData, localFavoriteData)
    }

    private fun joinFavoriteData(
        remoteData: ArrayList<Production>,
        localFavoriteData: ArrayList<String>?
    ): List<Production> {
        val result: ArrayList<Production> = arrayListOf()

        remoteData.forEach { production ->
            localFavoriteData?.forEach { localKey ->
                if (production.key == localKey) {
                    result.add(
                        ProductionItem(
                            _key = production.key,
                            _categoryKey = production.categoryKey,
                            _name = production.name,
                            _price = production.price,
                            _thumbnail = production.thumbnail,
                            _order = production.order,
                            _isFavorite = true
                        )
                    )
                }
            }
        }

        return result
    }

    private fun updateProductionLiveData(data: List<Production>) {
        PreferencesUtil.getStringArrayPref(app)?.let {
            favoriteData.value = ProductionData(
                getOnlyFavoriteData(
                    data as ArrayList<Production>,
                    PreferencesUtil.getStringArrayPref(app) as ArrayList<String>
                )
            )
        }
    }

    fun getFavorite() {
        updateProgress(true)

        if (productionDataByFiller.isNotEmpty()) {
            updateProductionLiveData(productionDataByFiller)
            updateProgress(false)
        } else {
            viewModelScope.launch {
                try {
                    val response = getProductionsUseCase.invoke()
                    updateProductionLiveData(response.productions)
                    productionDataByFiller = response.productions

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
}