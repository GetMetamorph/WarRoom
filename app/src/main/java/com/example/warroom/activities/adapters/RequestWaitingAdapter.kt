package com.example.warroom.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request
import com.example.warroom.activities.view_holders.RequestWaitingViewHolder

class RequestWaitingAdapter(val values: List<Request>) : RecyclerView.Adapter<RequestWaitingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestWaitingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestWaitingViewHolder(inflater.inflate(
            R.layout.recycler_view_welcome, parent, false))
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: RequestWaitingViewHolder, position: Int) {
        holder.bindValue(values[position])
    }
}