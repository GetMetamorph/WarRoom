package com.example.warroom.activities.view_holders

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.DetailSendActivity
import com.example.warroom.activities.Request


class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val user_name: TextView = view.findViewById(R.id.user_name)
    val category: TextView = view.findViewById(R.id.category)
    val v = view


    fun bindValue(request: Request) {
        //user_name.text = "" //TODO :request.user_name
        category.text = request.name
    }

    fun openPage(request: Request, context: Context){
        v.setOnClickListener {
            //if()
            val intent = DetailSendActivity.newIntent(context, request)
            context.startActivity(intent)
            //else if
            //else
        }
    }
}