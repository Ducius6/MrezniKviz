package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.Quizz
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(Intent(this@WaitFromNotificationActivity, QuestionActivity::class.java))
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        FirebaseDatabase.getInstance().reference.child(quizz.id).child("start").addValueEventListener(listener)
    }
}
