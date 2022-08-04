package com.jeongyookgak.jth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeongyookgak.jth.data.api.RESULT_OK
import com.jeongyookgak.jth.data.model.ProductionData
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.domain.model.remote.ProductionsRepo
import com.jeongyookgak.jth.domain.usecase.GetProductionsUseCase
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.di.PreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    app: Application,
    private val getProductionsUseCase: GetProductionsUseCase
) : BaseViewModel(app) {
    val productionData = MutableLiveData<ProductionData>()

    private fun getOnlyFavoriteData(
        remoteData: ArrayList<Production>,
        localFavoriteData: ArrayList<String>?
    ): List<Production> {
        val result: List<Production>

        val joinData = joinFavoriteData(remoteData, localFavoriteData)

        result = joinData.filter {
            it.isFavorite
        }

        return result
    }

    private fun joinFavoriteData(
        remoteData: ArrayList<Production>,
        localFavoriteData: ArrayList<String>?
    ): List<Production> {
        val result: ArrayList<Production> = remoteData

        remoteData.forEach {
            it.isFavorite = false
        }

        remoteData.forEachIndexed { index, production ->
            localFavoriteData?.forEach { localKey ->
                if(production.key == localKey) {
                    result[index].isFavorite = true
                }
            }
        }

        return result
    }

     fun getFavorite() {
        try {
            updateProgress(true)
            viewModelScope.launch {
                val response = getProductionsUseCase.invoke()

                if (response.code == RESULT_OK) {
                    productionData.value = ProductionData(
                        getOnlyFavoriteData(
                            response.productions as ArrayList<Production>,
                            PreferencesUtil.getStringArrayPref(app) as ArrayList<String>
                        )
                    )
                }
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