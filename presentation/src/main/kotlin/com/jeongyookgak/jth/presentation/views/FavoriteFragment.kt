package com.jeongyookgak.jth.presentation.views

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jeongyookgak.jth.presentation.JeongYookGakApplication.Companion.setSearchWord
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.FavoriteFragmentBinding
import com.jeongyookgak.jth.presentation.di.PreferencesUtil.getSearchText
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
        viewModel.searchText.value = getSearchText(activity)
        progress = JeongYookGakLoading(activity!!)
    }

    override fun onResume() {
        super.onResume()
        val searchText = getSearchText(activity)

        if (searchText.isNullOrEmpty()) {
            viewModel.getFavorite()
        } else {
            viewModel.findSearWord(searchText)
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
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initializeView() {
        binding?.productionSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.needRefresh = true

                if (s.toString().isEmpty()) {
                    viewModel.getFavorite()
                } else {
                    viewModel.findSearWord(s.toString())
                }

                setSearchWord(context, s.toString())
            }
        })
    }
}