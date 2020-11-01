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
import com.kmnvxh222.task8.utils.SharedPreferencesSettings
import com.kmnvxh222.task8.utils.TempMapper
import com.kmnvxh222.task8.viewmodel.ViewModelFactory
import com.kmnvxh222.task8.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        listenerNavigation()
        return binding.root
    }

    private fun listenerNavigation() {
        binding.floatingActionButton.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.cityFragment2)
        }

        binding.buttonSetting.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.settingsFragment)
        }
    }

    private fun setWeather(city: String) {
            viewModel.getWeatherDate(city).observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    adapter = WeatherRecyclerAdapter(it.list, requireContext())
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
                            Glide.with(this).load("https://openweathermap.org/img/wn/$icon.png").into(binding.imageViewWeather)

                            val setting = SharedPreferencesSettings().getSetting(requireContext())
                            val temp = TempMapper().convertTemp(setting,i.main.temp)
                            binding.textViewTemp.text = temp

                            binding.textViewWeather.text = i.weather[0].description
                        }
                    }

                    binding.textViewCity.text = it.city.name

                }
            })
    }

    private fun getCity() {
        viewModel.getAllCity()?.observe(viewLifecycleOwner, Observer {
            val cityName = checkDate(it)
            if (cityName == null || it.isEmpty()) {
                setWeather("Minsk")
            } else if (cityName != null) {
                setWeather(cityName)
            }

        })
    }

    private fun checkDate(list: List<City>): String? {
        val currentDate = System.currentTimeMillis()
        var listDate: MutableList<Long>? = ArrayList()
        var date: Long? = null
        var cityName: String? = null
        for (i in list) {
            listDate?.add(i.date)
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.close()
    }
}