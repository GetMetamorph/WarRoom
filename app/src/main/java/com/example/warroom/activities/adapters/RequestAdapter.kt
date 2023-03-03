package com.example.warroom.activities.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request
import com.example.warroom.activities.view_holders.RequestViewHolder
import com.example.warroom.adapters.ChallengeAdapter

class RequestAdapter(val context: Context) : RecyclerView.Adapter<RequestViewHolder>() {
    private var values: List<Request>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater.inflate(
            R.layout.recycler_view_welcome, parent, false))
    }

    override fun getItemCount(): Int {
        return values?.size ?: 0
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {

        values?.let {
            holder.bindValue(it[holder.adapterPosition])
            holder.openPage(it[holder.adapterPosition], context)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapterItems(challenges: List<Request>?) {
        values = challenges
        notifyDataSetChanged()
    }
}