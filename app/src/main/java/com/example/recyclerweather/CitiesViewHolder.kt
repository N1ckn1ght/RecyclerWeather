package com.example.recyclerweather

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.findViewById<TextView>(R.id.city)

    init {
        itemView.setOnClickListener {

        }
    }
}
