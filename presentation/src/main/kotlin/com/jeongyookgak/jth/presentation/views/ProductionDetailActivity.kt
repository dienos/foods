package com.jeongyookgak.jth.presentation.views

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jeongyookgak.jth.domain.model.remote.Production
import com.jeongyookgak.jth.presentation.JeongYookGakApplication
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.ProductionDetailActivityBinding
import com.jeongyookgak.jth.presentation.viewmodels.ProductionDetailViewModel
import com.jeongyookgak.jth.presentation.views.ListFragment.Companion.PUT_EXTRA_DETAIL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductionDetailActivity : BaseActivity<ProductionDetailActivityBinding>() {
    private val _viewModel: ProductionDetailViewModel by viewModels()
    private val viewModel: ProductionDetailViewModel
        get() = _viewModel

    private lateinit var progress: JeongYookGakLoading

    override fun getLayoutResId(): Int = R.layout.production_detail_activity

    override fun initializeViewModel() {
        progress = JeongYookGakLoading(this)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

        intent.getSerializableExtra(PUT_EXTRA_DETAIL)?.apply {
            viewModel.productionData.value = this as Production
        }
    }

    override fun initializeUiEvent() {
        binding?.lifecycleOwner?.lifecycleScope?.launch {
            viewModel.progressFlow.collectLatest { isShowing ->
                if (isShowing) {
                    progress.show()
                } else {
                    progress.dismiss()
                }
            }

            viewModel.toastFlow.collectLatest { text ->
                Toast.makeText(this@ProductionDetailActivity, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initializeView() {
        binding?.favoriteCheck?.setOnCheckedChangeListener { _, isChecked ->
            val data = viewModel.productionData.value

            data?.let {
                JeongYookGakApplication.controlFavoriteList(
                    context = this,
                    isChecked = isChecked,
                    data = it
                )
            }
        }
    }
}