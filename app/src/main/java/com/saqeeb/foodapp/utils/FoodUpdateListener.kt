package com.saqeeb.foodapp.utils

import com.saqeeb.foodapp.db.entities.FoodItem

interface FoodUpdateListener {
    fun foodUpdateClick(foodItem: FoodItem)
}