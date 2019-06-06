package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.JsonCategory
import retrofit.http.GET
import retrofit.http.Query

interface RestService {

    @GET("/category")
    fun getQuizzQuestions(@Query("id") id: Long?): JsonCategory?


}