package com.weather.forecast.clearsky.mainscreen.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.forecast.clearsky.R
import com.weather.forecast.clearsky.databinding.ActivityMainBinding
import com.weather.forecast.clearsky.mainscreen.viewmodel.MainViewModel
import com.weather.forecast.clearsky.network.ResultData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val input = findViewById<EditText>(R.id.searchbar_input)
        val searchBtn = findViewById<ImageButton>(R.id.searchbar_btn)

        searchBtn.setOnClickListener {

            viewModel.getWeatherData(input.text.toString()).observe(this) {
                when (it) {
                    is ResultData.Success -> {
                        Log.i("harry", "" + it.toString())
                        it.data?.let { it1 -> Log.d("TAG", "onCreate: $it1") }

                        binding.currentData.visibility = View.VISIBLE

                        binding.city.text = "${it.data?.location?.name}"
                        binding.country.text = "${it.data?.location?.country}"
                        binding.tempC.text = "Temp : ${it.data?.current?.temp_c} °C"
                        binding.windKph.text = "Wind : ${it.data?.current?.wind_kph} kph"
                        binding.currentCondition.text = it.data?.current?.condition?.text.toString()
                        binding.lastUpdated.text =
                            "Last updated:\n${it.data?.current?.last_updated}"

                        binding.recyclerViewForecast.visibility = View.VISIBLE


                        var forecastPrediction = Array(3) { Array(4) { "" } }
                        for (i in 0..2) {
                            forecastPrediction[i][0] =
                                it.data?.forecast?.forecastday?.get(i)?.day?.maxtemp_c.toString()
                            forecastPrediction[i][1] =
                                it.data?.forecast?.forecastday?.get(i)?.day?.mintemp_c.toString()
                            forecastPrediction[i][2] =
                                it.data?.forecast?.forecastday?.get(i)?.day?.avghumidity.toString()
                            forecastPrediction[i][3] =
                                it.data?.forecast?.forecastday?.get(i)?.day?.condition?.text.toString()
                        }

                        val calenderDay = listOf<String>("Today", "Tomorrow", "Day After Tomorrow")

                        val adapter = ForecastRecyclerViewAdapter(forecastPrediction, calenderDay)
                        val recyclerView: RecyclerView = binding.recyclerViewForecast
                        recyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                        recyclerView.adapter = adapter

                    }

                    is ResultData.Failed -> {
                        Log.d("TAG", "onCreate: failed ${it.message}")
                        binding.city.text = "Invalid location\nPlease try again"
                        binding.country.text = ""
                        binding.tempC.text = ""
                        binding.windKph.text = ""
                        binding.currentCondition.text = ""
                        binding.lastUpdated.text = ""
                        binding.recyclerViewForecast.visibility = View.GONE

                    }

                    is ResultData.Loading -> {
                        Log.d("TAG", "onCreate: Loading")
                    }
                }
            }
        }

    }
}

fun setData() {

}
