package com.example.recyclerweather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    internal var holderPrevious: CitiesViewHolder? = null

    private lateinit var iv: ImageView
    private lateinit var tv: TextView

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

        val cities = resources.getStringArray(R.array.cities).toMutableList()
        val rview = findViewById<RecyclerView>(R.id.cities)
        val citiesAdapter = CitiesAdapter(LayoutInflater.from(this), this, width)
        citiesAdapter.submitList(cities)
        rview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rview.adapter = citiesAdapter

        iv = findViewById(R.id.weather_icon)
        tv = findViewById(R.id.temperature)
    }

    internal fun updateWeather(city: String) {
        holderPrevious?.let {
            it.tv.setBackgroundResource(R.drawable.border)
            it.tv.setTextColor(Color.parseColor("#FF000000"))
        }
        GlobalScope.launch (Dispatchers.IO) {
            loadWeather(city)
        }
    }

    private fun loadWeather(city: String) {
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${getString(R.string.api_key)}&units=metric"

        var temp: String = getString(R.string.no_data)
        var wicon: String? = null

        try {
            val stream = URL(weatherURL).content as InputStream
            val rawData = Scanner(stream).nextLine().trimIndent()
            val weatherData: WeatherJSON = Gson().fromJson(rawData, WeatherJSON::class.java)
            temp = weatherData.main.temp
            wicon = weatherData.weather[0].icon
        } catch (e: FileNotFoundException) {
            this@MainActivity.runOnUiThread {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        } finally {
            this@MainActivity.runOnUiThread {
                tv.text = temp
                if (wicon == null) {
                    iv.setImageResource(0)
                } else {
                    Picasso.with(this).load("http://openweathermap.org/img/wn/${wicon}@2x.png").into(iv)
                }
            }
        }
    }
}