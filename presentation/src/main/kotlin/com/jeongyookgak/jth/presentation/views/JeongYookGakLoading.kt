package com.jeongyookgak.jth.presentation.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import com.jeongyookgak.jth.presentation.R
import com.jeongyookgak.jth.presentation.databinding.ProgressBarBinding
import javax.inject.Inject

class JeongYookGakLoading @Inject constructor(activity: Activity) {
    private var binding : ProgressBarBinding ?= null
    private var dialog: CustomDialog

    fun show(title: String = "") {
        binding?.title?.text = title
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    init {
        binding = ProgressBarBinding.inflate(activity.layoutInflater)

        setColorFilter(
            binding?.progressCircular?.indeterminateDrawable!!,
            ResourcesCompat.getColor(activity.resources, R.color.black, null)
        )

        dialog = CustomDialog(activity)
        dialog.setContentView(binding?.root!!)
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context,  android.R.style.Theme_Dialog) {
        init {
            window?.decorView?.rootView?.setBackgroundResource(android.R.color.transparent)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }
    }
}