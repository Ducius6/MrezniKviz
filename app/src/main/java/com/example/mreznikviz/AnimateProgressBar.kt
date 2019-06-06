package com.example.mreznikviz

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar

class AnimateProgressBar() {

    fun animateProgressBar(progressBar: ProgressBar?, startValue: Int, endValue: Int, duration: Int): Void? {
            val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", startValue, endValue)
        progressAnimator.duration = duration.toLong()
        progressAnimator.interpolator = LinearInterpolator()
            progressAnimator.start()
        return null
        }
}