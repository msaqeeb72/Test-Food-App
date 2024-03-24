package com.saqeeb.foodapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.databinding.CartItemLayoutBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.throttleClicks
import com.saqeeb.foodapp.utils.FoodItemListener

class CartListAdapter(private var dataList: ArrayList<FoodItem>,private val foodItemListener: FoodItemListener) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateDataList(dataList: ArrayList<FoodItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            val foodItem = dataList[position]
            binding.foodItem = foodItem

            binding.apply {
                qtyMinus.setOnClickListener {
                    if(foodItem.cartQty > 0) {
                        it.throttleClicks()
                        foodItem.cartQty = foodItem.cartQty.dec()
                        if(foodItem.cartQty == 0) {
                            foodItem.inCart = false
                            dataList.removeAt(position)
                            notifyItemRemoved(position)
                        }else
                            notifyItemChanged(position)
                        foodItemListener.onFoodUpdate(foodItem)

                    }
                }
                qtyPlus.setOnClickListener {
                        it.throttleClicks()
                        foodItem.cartQty = foodItem.cartQty.inc()
                        notifyItemChanged(position)
                    foodItemListener.onFoodUpdate(foodItem)

                }
            }
        }

    }

    inner class ViewHolder(val binding: CartItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}