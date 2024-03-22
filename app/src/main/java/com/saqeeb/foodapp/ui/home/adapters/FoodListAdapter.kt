package com.saqeeb.foodapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.databinding.FoodItemLayoutBinding
import com.saqeeb.foodapp.db.entities.FoodItem

class FoodListAdapter(private var dataList: ArrayList<FoodItem>) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun updateDataList(dataList: List<FoodItem>){
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            holder.binding.foodItem = dataList[position]
            binding.root.setOnClickListener {
//                listener.itemClicked(position)
            }
        }
    }

    inner class ViewHolder(val binding: FoodItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}