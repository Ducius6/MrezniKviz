package com.example.mreznikviz.entities

class FBUser() {
    var userName: String? = null

    override fun equals(other: Any?): Boolean{
        if (other?.javaClass != javaClass) return false
        other as FBUser
        if (!userName.equals(other.userName)) return false
        return true
    }

}