package com.example.warroom.models


data class User(val uid: String?,
                val email: String?,
                val username: String?,
                val picture: String?) {
    constructor() : this(null,null,null,null)
}