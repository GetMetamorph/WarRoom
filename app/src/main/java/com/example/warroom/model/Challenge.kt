package com.example.warroom.model

data class Challenge(
    val name: String = "",
    val receiver_id:String = "",
    val receiver_score:Int = 0,
    val sender_id:String = "",
    val sender_score:Int = 0
)
