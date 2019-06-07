package com.example.mreznikviz.usernet.userretrofit

import com.example.mreznikviz.entities.User
import org.springframework.http.ResponseEntity
import retrofit.client.Response
import retrofit.http.*
import java.util.*

interface UserRestService {

    @GET("/users")
    fun findAll(@Query("from")from:Int, @Query("size")size:Int):List<User>

    @POST("/users")
    fun registerUser(@Body user:User):Response

    @POST("/users/login")
    fun loginUser(@Query("username")username:String, @Query("password")password:String):Boolean

}