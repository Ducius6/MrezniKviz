package com.example.mreznikviz

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.Quizz
import kotlinx.android.synthetic.main.activity_wait_from_notification.*

class WaitFromNotificationActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_from_notification)

        val quizz = intent.getSerializableExtra("quiz") as Quizz

        editTextQuizTitle.text = quizz.admin + "'s quiz"
        themeTextView.text = "Theme: " + quizz.questions[0].category.title

        BounceAnimation(circleSmaller).withDuration(3300).isRepeatable(true).withAmplitude(0.5).executeSingleEvent()
        BounceAnimation(circleBigger).withDuration(3300).isRepeatable(true).withAmplitude(0.85).withDelay(450).executeSingleEvent()



    }
}
