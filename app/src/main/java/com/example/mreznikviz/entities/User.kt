package com.example.mreznikviz.entities

import java.io.Serializable

data class User (
    var id : Long,
    var name : String,
    var username : String,
    var email : String,
    var password : String,
    var score : Long
) : Serializable