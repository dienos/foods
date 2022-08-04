package com.jeongyookgak.jth.presentation.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.util.AnimationUtil
import com.jeongyookgak.jth.presentation.util.NetworkUtil
import com.jeongyookgak.jth.presentation.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject lateinit var networkUtil: NetworkUtil
    @Inject lateinit var aniUtil: AnimationUtil

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkUtil.registerNetworkCallback()

        setContentView(R.layout.splash_activity)

        if (networkUtil.checkNetwork()) {
            val handler = Handler(mainLooper)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val content: View = findViewById(android.R.id.content)
                content.viewTreeObserver.addOnPreDrawListener(
                    object : ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            return if (viewModel.complete.value) {
                                content.viewTreeObserver.removeOnPreDrawListener(this)
                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                finish()
                                true
                            } else {
                                false
                            }
                        }
                    }
                )

                handler.postDelayed({
                    viewModel.updateComplete()
                }, 500)
            } else {
                aniUtil.playAnimation(findViewById(R.id.anim)) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        } else {
            networkUtil.networkNotConnect()
        }
    }
}