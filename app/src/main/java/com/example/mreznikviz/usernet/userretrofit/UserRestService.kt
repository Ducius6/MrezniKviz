package com.example.mreznikviz.usernet.userretrofit

import com.example.mreznikviz.entities.User
import retrofit.client.Response
import retrofit.http.*

interface UserRestService {

    @GET("/users")
    fun findAll(@Query("from") from: Int, @Query("size") size: Int): List<User>

    @POST("/users")
    fun registerUser(@Body user: User): Response

    @PATCH("/users")
    fun updateUser(@Query("score") score: Long, @Query("userName") userName: String): Response

    @GET("/users/login")
    fun loginUser(@Query("userName") userName: String, @Query("password") password: String): User

    @GET("/users/sendpushmessage")
    fun sendNotification(@Query("token") token: String, @Query("quizId") quizId: String): Response

}