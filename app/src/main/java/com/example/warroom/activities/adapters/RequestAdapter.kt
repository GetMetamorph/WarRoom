package com.example.warroom.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request
import com.example.warroom.activities.view_holders.RequestViewHolder

class RequestAdapter(val values: List<Request>, val context: Context) : RecyclerView.Adapter<RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater.inflate(
            R.layout.recycler_view_welcome, parent, false))
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bindValue(values[position])
        holder.openPage(values[position], context)
    }
}