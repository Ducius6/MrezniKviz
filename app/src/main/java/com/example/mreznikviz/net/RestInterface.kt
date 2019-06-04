package com.example.mreznikviz.net

import com.example.mreznikviz.entities.Quizz

interface RestInterface {
    fun getQuizzQuestions(quizzId: Long?): Quizz?
}