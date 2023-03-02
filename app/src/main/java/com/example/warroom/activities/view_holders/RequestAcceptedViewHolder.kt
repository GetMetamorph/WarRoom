package com.example.warroom.activities.view_holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request

class RequestAcceptedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val user_name: TextView = view.findViewById(R.id.user_name)
    val category: TextView = view.findViewById(R.id.category)


    fun bindValue(request: Request) {
        user_name.text = request.user_name
        category.text = request.category
    }
}