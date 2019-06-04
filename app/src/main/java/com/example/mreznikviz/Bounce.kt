package com.example.mreznikviz

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.example.mreznikviz.listener.OnFinishListener

class BounceAnimation @JvmOverloads constructor(private val view: View, private val amplitude: Double = 1.0) {

    private lateinit var onFinishListener: OnFinishListener

    fun addOnFinishListener(onFinishListener: OnFinishListener): BounceAnimation {
        this.onFinishListener = onFinishListener
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun enable() {

        val valueAnimator = ValueAnimator.ofInt(1, 628)
        valueAnimator.addUpdateListener { animation ->
            val valueOfAnimation = animation.animatedValue as Int
            val y = Math.pow(Math.E, -valueOfAnimation / 300.0) * Math.sin(valueOfAnimation / 100.0) * 0.5
            view.scaleX = (1 + amplitude * y).toFloat()
            view.scaleY = (1 + amplitude * y).toFloat()
        }
        valueAnimator.duration = 700
        valueAnimator.start()

    }

}
