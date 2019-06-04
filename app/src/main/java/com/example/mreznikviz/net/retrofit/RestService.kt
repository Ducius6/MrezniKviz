package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.JsonCategory
import com.example.mreznikviz.entities.Quizz
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Query

interface RestService {

    @GET("/category?id={id}")
    fun getQuizzQuestions(@Path("id") categoryId: Long?): JsonCategory?


}