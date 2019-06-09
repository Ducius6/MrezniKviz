package com.example.mreznikviz.entities

import java.io.Serializable

class FBUser(val userName: String) : Serializable {

    override fun equals(other: Any?): Boolean{
        if (other?.javaClass != javaClass) return false
        other as FBUser
        if (!userName.equals(other.userName)) return false
        return true
    }

    override fun hashCode(): Int {
        return userName.hashCode() ?: 0
    }
}