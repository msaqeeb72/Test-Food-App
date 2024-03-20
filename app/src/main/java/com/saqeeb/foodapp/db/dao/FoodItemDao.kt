package com.saqeeb.foodapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.saqeeb.foodapp.db.entities.FoodItem

@Dao
interface FoodItemDao {
    @Insert
    suspend fun insertFoodItem(foodItem: FoodItem)

    @Query("DELETE FROM food")
    suspend fun clearFoodTable()

    @Update
    suspend fun updateFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM food where category = :category")
    suspend fun getFoodByCategory(category: String)

}
