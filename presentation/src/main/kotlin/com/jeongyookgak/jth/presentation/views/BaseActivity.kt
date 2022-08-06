package com.jeongyookgak.jth.presentation.views

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jeongyookgak.jth.presentation.util.NetworkUtil
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutResId(): Int
    abstract fun initializeViewModel()
    abstract fun initializeUiEvent()
    abstract fun initializeView()

    @Inject lateinit var networkUtil: NetworkUtil

    var binding: T? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        networkUtil.registerNetworkCallback()
        initializeViewModel()
        initializeView()
        initializeUiEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onStop() {
        super.onStop()
        networkUtil.terminateNetworkCallback()
    }
}