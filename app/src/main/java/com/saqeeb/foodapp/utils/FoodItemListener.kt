package com.saqeeb.foodapp.utils

import com.saqeeb.foodapp.db.entities.FoodItem

interface FoodItemListener {
    fun onFoodUpdate(foodItem: FoodItem)

    fun onFoodClicked(foodItem: FoodItem)
}