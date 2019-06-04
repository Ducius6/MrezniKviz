package com.example.mreznikviz.net

import com.example.mreznikviz.entities.JsonCategory

interface RestInterface {
    fun getQuizzQuestions(quizzId: Long?): JsonCategory?
}