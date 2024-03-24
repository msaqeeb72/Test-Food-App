package com.saqeeb.foodapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.databinding.FoodItemLayoutBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.throttleClicks
import com.saqeeb.foodapp.utils.FoodItemListener

class FoodListAdapter(private var dataList: ArrayList<FoodItem>, private val foodItemListener: FoodItemListener, private val fromHome:Boolean) :
    RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FoodItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            binding.favButton.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    if (foodItem.isFavorite) R.drawable.icon_favorite_filled else R.drawable.icon_favorite
                )
            )
            binding.cartButton.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    if (foodItem.inCart) R.drawable.icon_shopping_cart_filled else R.drawable.icon_shopping_cart
                )
            )
            binding.root.setOnClickListener {
                foodItemListener.onFoodClicked(dataList[position])
            }

            binding.favButton.setOnClickListener {
                foodItem.isFavorite = foodItem.isFavorite.not()
                notifyItemChanged(position)
                foodItemListener.onFoodUpdate(foodItem)
                it.throttleClicks()
                if(!fromHome) {
                    dataList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            binding.cartButton.setOnClickListener {
                foodItem.inCart = foodItem.inCart.not()
                if(foodItem.inCart){
                    foodItem.cartQty = 1
                }else{
                    foodItem.cartQty = 0
                }
                notifyItemChanged(position)
                foodItemListener.onFoodUpdate(foodItem)
                it.throttleClicks()
            }
        }

    }

    inner class ViewHolder(val binding: FoodItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}