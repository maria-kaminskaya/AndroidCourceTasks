package com.kmnvxh222.task8.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task8.adapters.CityRecyclerAdapter
import com.kmnvxh222.task8.databinding.FragmentCityBinding
import com.kmnvxh222.task8.model.City
import com.kmnvxh222.task8.presenter.CityPresenter
import com.kmnvxh222.task8.presenter.CityPresenterInterface
import com.kmnvxh222.task8.repository.LocalRepository

class CityFragment() : Fragment() {

    private lateinit var binding: FragmentCityBinding
    private lateinit var presenter: CityPresenterInterface
    private lateinit var adapter: CityRecyclerAdapter
    private lateinit var listCity: List<City>
    private lateinit var addCityDialogFragment: DialogFragmentAddCity

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        addCityDialogFragment = DialogFragmentAddCity(requireContext())
        presenter = CityPresenter(localRepository = LocalRepository(requireContext()))
        adapterInitialisation()
        setClickFloatButton()
        return binding.root
    }

    private fun adapterInitialisation() {
        presenter.getAllCity()?.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
                listCity = it

            Log.d("CityFragment", " getAllCity $it")
                adapter = CityRecyclerAdapter(listCity)
                adapter.setOnItemClickListener(adapterClickListener)
                binding.recyclerViewCity.let { it ->
                    it.adapter = adapter
                    it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//                }

                adapter.setOnItemClickListener(adapterClickListener)
            }
        })
    }

    private val adapterClickListener = object : CityRecyclerAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            presenter.updateCity(listCity[position])
        }
    }

    private fun setClickFloatButton(){
        binding.floatingActionButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            addCityDialogFragment.show(manager!!, "AddCityDialog")
        }
    }


}