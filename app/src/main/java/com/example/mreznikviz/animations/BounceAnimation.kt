package com.example.mreznikviz.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

class BounceAnimation(private val view: View) {

    private var onFinishListener: (()->Unit)? = null
    private var delay = 0
    private var amplitude = 1.0
    private var duration = 750

    private var isCreated = false
    private var isRepeatable = false


    private var heightOfAnimation = 0.0

    fun withAmplitude(amplitude: Double): BounceAnimation {
        this.amplitude = amplitude
        return this
    }

    fun addOnFinishListener(onFinishListener: ()->Unit): BounceAnimation {
        this.onFinishListener = onFinishListener
        return this
    }

    fun withDuration(duration: Int): BounceAnimation {
        this.duration = duration
        return this
    }

    fun isRepeatable(r: Boolean): BounceAnimation {
        this.isRepeatable = r
        return this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun enableOnTouchDemand() {
        if (delay != 0) throw NumberFormatException("Delay must not be modified for on touch demand")
        if (duration != 750) throw NumberFormatException("Duration must not be modified for on touch demand")
        if (onFinishListener == null) onFinishListener = {}

        if (isCreated) throw NullPointerException("Animation already initialized for on touch demand or is running single event")

        isCreated = true

        view.setOnTouchListener { v, event ->
            if (v.isEnabled) {
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
                    if (event.action == MotionEvent.ACTION_UP) onFinishListener!!.invoke()
                }
            }
            true
        }
    }

    fun withDelay(delay: Int): BounceAnimation {
        this.delay = delay
        return this
    }

    fun executeSingleEvent() {
        if (onFinishListener != null) throw NumberFormatException("Listener must not be modified")
        // if (isCreated) throw NullPointerException("Animation already initialized for on touch demand or is running single event")
        isCreated = true

        val valueAnimator = ValueAnimator.ofInt(1, 628)
        valueAnimator.startDelay = delay.toLong()
        valueAnimator.addUpdateListener { animation ->
            val valueOfAnimation = animation.animatedValue as Int
            val y = Math.pow(Math.E, -valueOfAnimation / 300.0) * Math.sin(valueOfAnimation / 100.0) * 0.5
            view.scaleX = (1 + amplitude * y).toFloat()
            view.scaleY = (1 + amplitude * y).toFloat()
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isCreated = false
            }
        })
        valueAnimator.duration = duration.toLong()
        if (isRepeatable) valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.start()
    }

}
