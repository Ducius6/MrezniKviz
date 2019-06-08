package com.example.mreznikviz.quiznet

import com.example.mreznikviz.entities.JsonCategory

interface RestInterface {
    fun getQuizzQuestions(quizzId: Long?): JsonCategory?
}