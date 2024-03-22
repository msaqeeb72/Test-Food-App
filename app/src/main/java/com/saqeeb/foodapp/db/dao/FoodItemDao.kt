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

    @Query("SELECT * FROM food where is_favorite = 1")
    suspend fun getFavoviteList(): List<FoodItem>

    @Query("SELECT * FROM food where food_name LIKE '%'||:text||'%'")
    suspend fun searchFoodItem(text: String):List<FoodItem>

    @Query("SELECT * FROM food where food_name LIKE '%'||:text||'%' AND is_favorite = 1")
    suspend fun searchInFavorite(text: String):List<FoodItem>

}
