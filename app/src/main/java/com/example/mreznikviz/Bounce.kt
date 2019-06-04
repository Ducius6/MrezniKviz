package com.example.mreznikviz

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.example.mreznikviz.listener.OnFinishListener

class BounceAnimation @JvmOverloads constructor(private val view: View, private val amplitude: Double = 1.0) {

    private lateinit var onFinishListener: OnFinishListener
    private var heightOfAnimation = 0.0

    fun addOnFinishListener(onFinishListener: OnFinishListener): BounceAnimation {
        this.onFinishListener = onFinishListener
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun enable() {
        view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val valueAnimator = ValueAnimator.ofInt(1, 128)
                valueAnimator.addUpdateListener { animation ->
                    val valueOfAnimation = animation.animatedValue as Int
                    val y = Math.pow(Math.E, -valueOfAnimation / 300.0) * Math.sin(valueOfAnimation / 100.0) * 0.5
                    view.scaleX = (1 + amplitude * y).toFloat()
                    view.scaleY = (1 + amplitude * y).toFloat()
                    heightOfAnimation = y / 0.297745
                }
                valueAnimator.duration = 250
                valueAnimator.start()
            } else if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                val valueAnimator = ValueAnimator.ofInt(170, 628)
                valueAnimator.addUpdateListener { animation ->
                    val valueOfAnimation = animation.animatedValue as Int
                    val y = Math.pow(Math.E, -valueOfAnimation / 300.0) * Math.sin(valueOfAnimation / 100.0) * 0.5
                    view.scaleX = (1 + amplitude * heightOfAnimation * y).toFloat()
                    view.scaleY = (1 + amplitude * heightOfAnimation * y).toFloat()
                }
                valueAnimator.duration = 500
                valueAnimator.start()
                if (event.action == MotionEvent.ACTION_UP) onFinishListener.onComplete()
            }
            true
        }
    }
}
