package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mreznikviz.animations.BounceAnimation
import com.example.mreznikviz.entities.Question
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

        val admin = intent.getSerializableExtra("admin") as String
        val theme = intent.getSerializableExtra("theme") as String
        val quizId = intent.getSerializableExtra("id") as String

        editTextQuizTitle.text = admin + "'s quiz"
        themeTextView.text = "Theme: " + theme

        // TODO popravi ovo username od usera
        FirebaseDatabase.getInstance().reference.child("quiz/$quizId/members/USERNAME_OD_USERA").setValue(true)

        BounceAnimation(circleSmaller).withDuration(3300).isRepeatable(true).withAmplitude(0.5).executeSingleEvent()
        BounceAnimation(circleBigger).withDuration(3300).isRepeatable(true).withAmplitude(0.85).withDelay(450).executeSingleEvent()

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val listener2 = object : ValueEventListener {
                        override fun onDataChange(ds: DataSnapshot) {
                            val questions = mutableListOf<Question>()
                            ds.children.forEach{questions.add(it.getValue(Question::class.java)!!)}
                            startActivity(Intent(this@WaitFromNotificationActivity, QuestionActivity::class.java)
                                .putExtra("quiz", Quizz(quizId, listOf(), questions, admin)))
                            finish()
                        }
                        override fun onCancelled(p0: DatabaseError) {}
                    }
                    FirebaseDatabase.getInstance().reference.child("quiz/$quizId/questions").addValueEventListener(listener2)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        FirebaseDatabase.getInstance().reference.child("quiz/$quizId/start").addValueEventListener(listener)
    }
}
