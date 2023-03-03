package com.example.warroom.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warroom.R
import com.example.warroom.activities.Request
import com.example.warroom.activities.view_holders.RequestViewHolder

class RequestAdapter(val values: List<Request>, val listener: FormRequest) : RecyclerView.Adapter<RequestViewHolder>() {
    interface FormRequest {
        fun onClick(request: Request)
    }

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
        listener.onClick(values[position])
    }
}