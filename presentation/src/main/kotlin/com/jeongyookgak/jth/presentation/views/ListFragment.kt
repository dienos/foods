package com.jeongyookgak.jth.presentation.views

import androidx.fragment.app.viewModels
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.ListFragmentBinding
import com.jeongyookgak.jth.presentation.viewmodels.ProductionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<ListFragmentBinding>() {
    private val _viewModel: ProductionViewModel by viewModels()
    private val viewModel: ProductionViewModel
        get() = _viewModel

    override fun getLayoutResId(): Int = R.layout.list_fragment

    override fun initializeViewModel() {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        viewModel.getProductions()
    }
}