package com.example.mreznikviz.quiznet.quizretrofit

import com.example.mreznikviz.entities.JsonCategory
import com.example.mreznikviz.quiznet.RestFactory
import com.example.mreznikviz.quiznet.RestInterface
import retrofit.RestAdapter

class RestRetrofit : RestInterface {

    override fun getQuizzQuestions(quizzId: Long?): JsonCategory? {
        return service.getQuizzQuestions(quizzId)
    }

    val service : RestService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP_QUIZ
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()
        service = retrofit.create(RestService::class.java)
    }
}