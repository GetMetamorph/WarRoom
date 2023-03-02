package com.example.warroom.activities

class Request(val user_name: String, val category: String, val reason: String,val description: String){
    companion object {
        fun createRequest(): List<Request>{
            var list = mutableListOf<Request>()
            for(i in 0..20){
                list.add(Request("username", "Sportif", "Manque de respect","Personne peut me battre, je vous fume dans n'importe quelle domaine"))
            }
            return list
        }
    }
}