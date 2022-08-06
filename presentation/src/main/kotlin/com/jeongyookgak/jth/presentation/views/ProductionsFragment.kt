package com.jeongyookgak.jth.presentation.views

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.ProductionsFragmentBinding
import com.jeongyookgak.jth.presentation.viewmodels.ProductionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductionsFragment : BaseFragment<ProductionsFragmentBinding>() {
    private lateinit var progress: JeongYookGakLoading

    private val _viewModel: ProductionViewModel by viewModels()
    private val viewModel: ProductionViewModel
        get() = _viewModel

    override fun getLayoutResId(): Int = R.layout.productions_fragment

    override fun initializeViewModel() {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        activity?.let {
            progress = JeongYookGakLoading(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductions()
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
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}