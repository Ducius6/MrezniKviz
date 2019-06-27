package com.example.mreznikviz.entities

import java.io.Serializable

data class Question (
    var id: Long,
    var question: String,
    var answer: String
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (other?.javaClass != javaClass) return false
        other as Question
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}