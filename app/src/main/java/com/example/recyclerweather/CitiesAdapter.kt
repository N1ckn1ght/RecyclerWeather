package com.example.recyclerweather

import android.content.Context
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CitiesAdapter (private val inflater: LayoutInflater, private val context: Context, private val width: Int) : ListAdapter<String, CitiesViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val row: View = inflater.inflate(R.layout.item, parent, false)
        return CitiesViewHolder(row, context)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.tv.layoutParams.width = width
        holder.tv.text = getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<String> = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) : Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: String, newItem: String) : Boolean {
                return areItemsTheSame(oldItem, newItem)
            }
        }
    }
}
