package com.saqeeb.foodapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.repos.FoodRepo
import com.saqeeb.foodapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepo: FoodRepo): ViewModel() {

    val foodListStateFlow: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.foodList
    val favoriteListStateFlow: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.favoriteList

    init {

    }
    fun getFoodList(){
        viewModelScope.launch {
            foodRepo.getFoodList()
        }
    }

    fun updateFoodInDb(foodItem: FoodItem) {
        viewModelScope.launch {
            foodRepo.updateFoodItem(foodItem)
        }
    }

     fun searchFoodInDb(text:String): Deferred<ArrayList<FoodItem>> {
        return viewModelScope.async {
            foodRepo.searchFoodInDb(text)
        }
    }

     fun searchInFavorite(text:String): Deferred<ArrayList<FoodItem>> {
        return viewModelScope.async {
            foodRepo.searchInFavorite(text)
        }
    }

    fun getFavoriteFoods() {
        viewModelScope.launch {
            foodRepo.getFavoriteFoods()
        }
    }

}