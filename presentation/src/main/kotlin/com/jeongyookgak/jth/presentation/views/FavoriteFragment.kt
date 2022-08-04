package com.jeongyookgak.jth.presentation.views

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.FavoriteFragmentBinding
import com.jeongyookgak.jth.presentation.viewmodels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteFragmentBinding>() {
    private lateinit var progress: JeongYookGakLoading

    private val _viewModel: FavoriteViewModel by viewModels()
    private val viewModel: FavoriteViewModel
        get() = _viewModel

    override fun getLayoutResId(): Int = R.layout.favorite_fragment

    override fun initializeViewModel() {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        progress = JeongYookGakLoading(activity!!)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorite()
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