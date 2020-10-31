package com.kmnvxh222.task8.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmnvxh222.task8.R
import com.kmnvxh222.task8.model.City
import kotlinx.android.synthetic.main.item_city.view.textViewCityName

class CityRecyclerAdapter(private var cityList: List<City>) :
        RecyclerView.Adapter<CityRecyclerAdapter.CityViewHolder>() {

    private lateinit var mItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view, mItemClickListener)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
            holder.bind(cityList[position])

    class CityViewHolder(itemView: View, private val mItemClickListener: OnItemClickListener) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(city: City) {
            itemView.textViewCityName.text = city.content
        }

        override fun onClick(v: View) = mItemClickListener.onItemClick(v, adapterPosition)
    }

    fun updateList(cityList: List<City>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

}
