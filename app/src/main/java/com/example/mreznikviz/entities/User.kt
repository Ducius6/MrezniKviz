package com.example.mreznikviz.entities

import java.io.Serializable

data class User (
    var firstName : String,
    var userName : String,
    var email : String,
    var password : String,
    var score : Long
) : Serializable