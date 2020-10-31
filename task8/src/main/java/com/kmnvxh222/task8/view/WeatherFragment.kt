package com.kmnvxh222.task8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmnvxh222.task8.R
import com.kmnvxh222.task8.adapters.WeatherRecyclerAdapter
import com.kmnvxh222.task8.databinding.FragmentWeatherBinding
import com.kmnvxh222.task8.model.City
import com.kmnvxh222.task8.viewmodel.ViewModelFactory
import com.kmnvxh222.task8.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment() : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(WeatherViewModel::class.java)
        getCity()
        listenerNavigationToCity()
        return binding.root
    }

    private fun listenerNavigationToCity() {
        binding.floatingActionButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.cityFragment2)
        }
    }

    private fun setWeather(city: String) {
//        if (city != null) {
            viewModel.getWeatherDate(city).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter = WeatherRecyclerAdapter(it.list)

                    binding.recyclerViewWeather.let { it ->
                        it.adapter = adapter
                        it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    }

                    val currentDate = System.currentTimeMillis()
                    var listDate: List<Long>? = null
                    var date: Long? = null
                    for (i in it.list) {
                        listDate = listOf(i.dt)
                    }
                    if (listDate != null) {
                        date = getDateNearest(listDate, currentDate)
                    }
                    for (i in it.list) {
                        if (i.dt == date) {
                           val icon = i.weather[0].icon
                            Glide.with(this).load("http://openweathermap.org/img/wn/$icon.png").into(binding.imageViewWeather)
                            binding.textViewTemp.text = i.main.temp.toString()
                            binding.textViewWeather.text = i.weather[0].description
                        }
                    }

                    binding.textViewCity.text = it.city.name

                }
            })
//        }
    }

    private fun getCity() {
        var city: String = "Minsk"
        viewModel.getAllCity()?.observe(viewLifecycleOwner, Observer {
            if (checkDate(it) == null || it.isEmpty()) {
                city = "Minsk"
            } else if (checkDate(it) != null) {
                city = checkDate(it)!!

            }

        })
        setWeather(city)
    }

    private fun checkDate(list: List<City>): String? {
        val currentDate = System.currentTimeMillis()
        var listDate: List<Long>? = null
        var date: Long? = null
        var cityName: String? = null
        for (i in list) {
            listDate = listOf(i.date)
        }
        if (listDate != null) {
            date = getDateNearest(listDate, currentDate)
        }
        for (i in list) {
            if (i.date == date) {
                cityName = i.content
            }
        }
        return cityName
    }

    private fun getDateNearest(dates: List<Long>, targetDate: Long): Long {
        return TreeSet(dates).lower(targetDate)
    }
}