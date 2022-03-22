package com.example.recyclerweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class CitiesAdapter (private val inflater: LayoutInflater, private val width: Int, private val padding: Int) : ListAdapter<Int, CitiesViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val row: View = inflater.inflate(R.layout.item, parent, false)
        parent.setPadding(padding, 0, padding, 0)
        return CitiesViewHolder(row)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.tv.layoutParams.width = width
        holder.bindTo(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Int> = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int) : Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Int, newItem: Int) : Boolean {
                return areItemsTheSame(oldItem, newItem)
            }
        }
    }
}
