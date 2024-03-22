package com.saqeeb.foodapp.repos

import com.saqeeb.foodapp.apis.FoodAPI
import com.saqeeb.foodapp.db.dao.FoodItemDao
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.isValid
import com.saqeeb.foodapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FoodRepo @Inject constructor(private val foodAPI: FoodAPI,private val foodItemDao: FoodItemDao) {
    private val _foodList = MutableStateFlow<NetworkResult<ArrayList<FoodItem>>>(NetworkResult.Init())
    val foodList:StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = _foodList


    private val _favoriteList = MutableStateFlow<NetworkResult<ArrayList<FoodItem>>>(NetworkResult.Init())
    val favoriteList:StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = _favoriteList
    suspend fun getFoodList(){
        _foodList.emit(NetworkResult.Loading())
        val response = foodAPI.getAllFood("foods")
        if(response.isValid()){
            _foodList.emit(NetworkResult.Success(response.body()!![0]))
            addAllFoodItems(response.body()!![0])
        }else{
            _foodList.emit(NetworkResult.Error("Something went wrong (${response.code()})"))
        }
    }
    suspend fun getFoodByCategory(category: String):ArrayList<FoodItem>?{
        _foodList.emit(NetworkResult.Loading())
        val response = foodAPI.getAllFood("\$.foods[?(@.category==\"$category\")]")
        return if(response.isValid()){
            response.body()!![0]
        }else{
            null
        }
    }

    suspend fun addFoodItem(foodItem: FoodItem){
        foodItemDao.insertFoodItem(foodItem)
    }
    suspend fun updateFoodItem(foodItem: FoodItem){
        foodItemDao.updateFoodItem(foodItem)
    }
    suspend fun addAllFoodItems(foodItems: ArrayList<FoodItem>){
        foodItems.forEach {
            val result = addFoodItem(it)
        }
    }
    suspend fun getFoodByCategoryFromDb(category: String):ArrayList<FoodItem>{
        return ArrayList(foodItemDao.getFoodByCategory(category))
    }

    suspend fun searchFoodInDb(text: String):ArrayList<FoodItem> {
        return ArrayList(foodItemDao.searchFoodItem(text))

    }
    suspend fun searchInFavorite(text: String):ArrayList<FoodItem> {
        return ArrayList(foodItemDao.searchInFavorite(text))

    }

    suspend fun getFavoriteFoods() {
        _favoriteList.emit(NetworkResult.Loading())
        val response = foodItemDao.getFavoviteList()
        _favoriteList.emit(NetworkResult.Success(ArrayList(response)))
    }
}