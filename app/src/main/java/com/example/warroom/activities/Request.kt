package com.example.warroom.activities

class Request(
    val name: String?,
    val receiver_id: String?,
    val receiver_score: Long?,
    val sender_id: String?,
    val sender_score: Long?,
    val completed: Boolean?
){

    constructor() : this(null,null,null,null,null,null)
}