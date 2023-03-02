package com.example.warroom.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request
import com.example.warroom.activities.view_holders.RequestAcceptedViewHolder

class RequestAcceptedAdapter(val values: List<Request>) : RecyclerView.Adapter<RequestAcceptedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestAcceptedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestAcceptedViewHolder(inflater.inflate(
            R.layout.recycler_view_welcome, parent, false))
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: RequestAcceptedViewHolder, position: Int) {
        holder.bindValue(values[position])
    }
}