package com.example.mreznikviz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView
import com.example.mreznikviz.entities.JsonCategory

class Question : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var counter: QuestionCounter? = null
    var textViewQuestion: TextView?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var category = intent.getSerializableExtra("category") as JsonCategory


        progressBar = findViewById(R.id.progressBarQuestion);
        progressBar?.progress = 0

        textViewQuestion = findViewById(R.id.textViewQuestion)

        counter = QuestionCounter(progressBar!!, textViewQuestion!!, category)
        counter?.execute()
    }
}
