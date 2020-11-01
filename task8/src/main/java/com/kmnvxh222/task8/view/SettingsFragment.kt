package com.kmnvxh222.task8.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kmnvxh222.task8.databinding.FragmentSettingsBinding
import com.kmnvxh222.task8.viewmodel.SettingViewModel
import com.kmnvxh222.task8.viewmodel.ViewModelSettingFactory

class SettingsFragment() : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelSettingFactory()).get(SettingViewModel::class.java)
        setValueSwitch()
        getSettingSwitch()
        return binding.root
    }

    private fun setValueSwitch() {
       binding.switchCompat.isChecked = viewModel.getSetting(requireContext())!!
    }

    private fun getSettingSwitch(){
        binding.switchCompat.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            viewModel.saveSetting(b,requireContext())
        }
    }

}