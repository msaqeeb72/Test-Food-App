package com.saqeeb.foodapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saqeeb.foodapp.db.entities.FoodItem

@Dao
interface FoodItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFoodItem(foodItem: FoodItem):Long


    @Update
    suspend fun updateFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM food where category = :category")
    suspend fun getFoodByCategory(category: String): List<FoodItem>

}
