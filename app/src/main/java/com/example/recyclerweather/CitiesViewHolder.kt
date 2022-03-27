package com.example.recyclerweather

import android.content.Context
import android.graphics.Color.parseColor
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitiesViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
    val tv: TextView = itemView.findViewById(R.id.city)

    init {
        itemView.setOnClickListener {
            tv.setBackgroundResource(R.color.black)
            tv.setTextColor(parseColor("#FFFFFFFF"))
            (context as MainActivity).updateWeather(tv.text.toString(), this == context.holderPrevious)
            context.holderPrevious = this
        }
    }
}
