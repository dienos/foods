package com.jeongyookgak.jth.presentation.views

import androidx.activity.viewModels
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.MainActivityBinding
import com.jeongyookgak.jth.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<MainActivityBinding>() {
    private val _viewModel: MainViewModel by viewModels()
    private val viewModel: MainViewModel
        get() = _viewModel

    override fun getLayoutResId(): Int = R.layout.main_activity

    override fun initializeViewModel() {
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        viewModel.getProductions()
    }

    override fun initializeUiEvent() {}
}