package com.example.mreznikviz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mreznikviz.entities.JsonCategory
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var category = intent.getSerializableExtra("category") as JsonCategory

        textViewQuestion.text = category.clues!![0].question
    }
}
