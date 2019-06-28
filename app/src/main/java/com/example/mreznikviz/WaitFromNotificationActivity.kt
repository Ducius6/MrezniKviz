package com.example.mreznikviz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

        val admin = intent.getStringExtra("admin")
        val theme = intent.getStringExtra("theme")
        val quizId = intent.getStringExtra("id")

        editTextQuizTitle.text = admin + "'s quiz"
        themeTextView.text = "Theme: " + theme

        FirebaseDatabase.getInstance().reference.child("quiz/$quizId/members/" + MainActivity.getUser().userName).setValue(true)

        BounceAnimation(circleSmaller).withDuration(4500).isRepeatable(true).withAmplitude(0.5).executeSingleEvent()
        BounceAnimation(circleBigger).withDuration(4500).isRepeatable(true).withAmplitude(0.85).withDelay(600).executeSingleEvent()

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val nop = dataSnapshot.getValue(Long::class.java)
                    val listener2 = object : ValueEventListener {
                        override fun onDataChange(ds: DataSnapshot) {
                            val questions = mutableListOf<Question>()
                            ds.children.forEach{questions.add(readQuestion(it))}
                            startActivity(Intent(this@WaitFromNotificationActivity, QuestionActivity::class.java)
                                .putExtra("questions", Quizz(quizId, listOf(), questions, admin, ""))
                                .putExtra("nop", nop))
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

    fun readQuestion(ds: DataSnapshot): Question {
        return Question(ds.child("id").getValue(Long::class.java)!!, ds.child("question").getValue(String::class.java)!!, ds.child("answer").getValue(String::class.java)!!)
    }
}
