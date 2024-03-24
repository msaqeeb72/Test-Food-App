package com.saqeeb.foodapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.databinding.CategoryItemBinding
import com.saqeeb.foodapp.utils.CategoryChangeListener

class CategoryAdapter(private var dataList: ArrayList<String>, private val categoryChangeListener: CategoryChangeListener, private var selectedIndex:Int) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateDataList(dataList: ArrayList<String>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun changeSelectedPosition(selectedIndex: Int){
        this.selectedIndex = selectedIndex
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            binding.category.text = dataList[position]
            binding.indicator.isVisible = position == selectedIndex
            binding.root.setOnClickListener {
                categoryChangeListener.onCategoryChanged(position,dataList[position])
            }
        }

    }

    inner class ViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}