package com.kmnvxh222.task8.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmnvxh222.task8.R
import com.kmnvxh222.task8.model.WeatherResponse
import com.kmnvxh222.task8.utils.SharedPreferencesSettings
import com.kmnvxh222.task8.utils.TempMapper
import kotlinx.android.synthetic.main.item_weather.view.imageViewWeather
import kotlinx.android.synthetic.main.item_weather.view.textViewTemp
import kotlinx.android.synthetic.main.item_weather.view.textViewTime
import kotlinx.android.synthetic.main.item_weather.view.textViewWeather
import java.text.SimpleDateFormat
import java.util.*

class WeatherRecyclerAdapter(private var weatherList: List<WeatherResponse.ListWeather>, context: Context) :
        RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder>() {


    val setting = SharedPreferencesSettings().getSetting(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) =
            holder.bind(weatherList[position], setting)

    class WeatherViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {


        fun bind(weatherResponse: WeatherResponse.ListWeather,setting: Boolean? ) {
            val itemIcon = weatherResponse.weather[0].icon

            Glide.with(itemView).load("https://openweathermap.org/img/wn/$itemIcon.png").into(itemView.imageViewWeather)


            val s = weatherResponse.dt_txt
            val format = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val docDate: Date = format.parse(s)
            val formatTime = SimpleDateFormat("hh:mm")
            val time = formatTime.format(docDate.time)
            itemView.textViewTime.text = time

            itemView.textViewWeather.text =  weatherResponse.weather[0].main
            
            val temp = TempMapper().convertTemp(setting, weatherResponse.main.temp)
            itemView.textViewTemp.text = temp
        }

    }

}
