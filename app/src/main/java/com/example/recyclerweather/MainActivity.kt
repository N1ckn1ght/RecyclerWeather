package com.example.recyclerweather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
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
    private lateinit var etCity: EditText
    private lateinit var ivWeatherIcon: ImageView
    private lateinit var tvTemperature: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindspeed: TextView
    private lateinit var rview: RecyclerView
    private lateinit var citiesAdapter: CitiesAdapter
    private lateinit var cities: MutableList<String>

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

        rview = findViewById(R.id.cities)
        etCity = findViewById(R.id.city)
        ivWeatherIcon = findViewById(R.id.weather_icon)
        tvTemperature = findViewById(R.id.temperature)
        tvHumidity = findViewById(R.id.humidity)
        tvWindspeed = findViewById(R.id.windspeed)

        citiesAdapter = CitiesAdapter(LayoutInflater.from(this), this, width)
        cities = resources.getStringArray(R.array.cities).toMutableList()
        updateAdapter()
    }

    internal fun updateWeather(city: String, same: Boolean) {
        if (!same) {
            holderPrevious?.let {
                it.tv.setBackgroundResource(R.drawable.border)
                it.tv.setTextColor(Color.parseColor("#FF000000"))
            }
        }
        GlobalScope.launch (Dispatchers.IO) {
            loadWeather(city)
        }
    }

    private fun updateAdapter() {
        citiesAdapter.submitList(cities)
        rview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rview.adapter = citiesAdapter
    }

    private fun loadWeather(city: String) {
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${getString(R.string.api_key)}&units=metric"

        var temperature: String = getString(R.string.no_data)
        var weatherIcon: String? = null
        var humidity: String = getString(R.string.no_data)
        var windSpeed: String = getString(R.string.no_data)

        try {
            val stream = URL(weatherURL).content as InputStream
            val rawData = Scanner(stream).nextLine().trimIndent()
            val weatherData: WeatherJSON = Gson().fromJson(rawData, WeatherJSON::class.java)
            temperature = weatherData.main.temp.toInt().toString() + "°"
            if (temperature.substring(0, 1) != "-" && temperature.substring(0, 1) != "0") {
                temperature = "+${temperature}"
            }
            weatherIcon = weatherData.weather[0].icon
            humidity = weatherData.main.humidity.toString() + "%"
            windSpeed = weatherData.wind.speed.toInt().toString() + " m/s"
        } catch (e: FileNotFoundException) {
            this@MainActivity.runOnUiThread {
                Toast.makeText(this, "No such city or internet connection", Toast.LENGTH_SHORT).show()
            }
        } finally {
            this@MainActivity.runOnUiThread {
                etCity.setText(city)
                tvTemperature.text = temperature
                if (weatherIcon == null) {
                    ivWeatherIcon.setImageResource(0)
                } else {
                    Picasso.with(this).load("http://openweathermap.org/img/wn/${weatherIcon}@2x.png").into(ivWeatherIcon)
                }
                tvHumidity.text = humidity
                tvWindspeed.text = windSpeed
            }
        }
    }

    fun onEnterClick(view: View) {
        val city = etCity.text.toString()
        if (cities.contains(city)) {
            // TODO -> maybe then this city should be selected in the RecyclerView
        } else {
            cities.add(city)
            updateAdapter()
        }
    }
}