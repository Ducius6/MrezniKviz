package com.example.mreznikviz.usernet

import com.example.mreznikviz.entities.User
import org.springframework.http.ResponseEntity
import retrofit.client.Response
import java.util.*

interface UserRestInterface {
    fun registerUser(user:User):Response
    fun findAll(from:Int, size:Int):List<User>
    fun loginUser(userName:String,password:String):User
    fun updateUser(score:Long, userName:String):Response
}