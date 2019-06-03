package com.example.mreznikviz.entities

import java.io.Serializable

data class Question (
    var id: Long,
    var category: Category,
    var question: String,
    var answer: String
) : Serializable