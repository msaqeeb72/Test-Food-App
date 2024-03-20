package com.saqeeb.foodapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saqeeb.foodapp.db.dao.FoodItemDao
import com.saqeeb.foodapp.db.entities.FoodItem

@Database(entities = [FoodItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodItemDao():FoodItemDao
}