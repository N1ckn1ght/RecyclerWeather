package com.example.recyclerweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(displayMetrics)
        }
        val width = (displayMetrics.widthPixels * 0.3).toInt()
        val padding = (displayMetrics.widthPixels * 0.1).toInt()

        val cities = resources.getStringArray(R.array.cities).toMutableList()
        val rview = findViewById<RecyclerView>(R.id.cities)
        val citiesAdapter = CitiesAdapter(LayoutInflater.from(this), width, padding)
        citiesAdapter.submitList(cities)
        rview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rview.adapter = citiesAdapter
    }
}