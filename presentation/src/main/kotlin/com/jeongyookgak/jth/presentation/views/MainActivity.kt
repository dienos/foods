package com.jeongyookgak.jth.presentation.views

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.MainActivityBinding
import com.jeongyookgak.jth.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>() {
    private val _viewModel: MainViewModel by viewModels()
    private val viewModel: MainViewModel
        get() = _viewModel

    private lateinit var progress: JeongYookGakLoading

    override fun getLayoutResId(): Int = R.layout.main_activity


    override fun initializeView() {
        progress = JeongYookGakLoading(this)

        val viewPager = binding?.viewPager
        val tabLayout = binding?.tabLayout

        viewPager?.isUserInputEnabled = false
        viewPager?.adapter = PagerFragmentStateAdapter(supportFragmentManager, lifecycle)

        if(viewPager != null && tabLayout != null) {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = viewModel.tabData[position]
            }.attach()
        }
    }

    override fun initializeViewModel() {
        progress = JeongYookGakLoading(this)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        viewModel.setTabData()
    }

    override fun initializeUiEvent() {
        binding?.lifecycleOwner?.lifecycleScope?.launch {
            viewModel.progressFlow.collectLatest  { isShowing ->
                if (isShowing) {
                    progress.show()
                } else {
                    progress.dismiss()
                }
            }

            viewModel.toastFlow.collectLatest  { text ->
                Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}