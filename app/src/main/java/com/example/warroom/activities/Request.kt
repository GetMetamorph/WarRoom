package com.example.warroom.activities

class Request(val user_name: String, val category: String){
    companion object {
        fun createRequest(): List<Request>{
            var list = mutableListOf<Request>()
            for(i in 0..20) {
                list.add(Request("username", "Sportif"))
            }
            return list
        }
    }
}