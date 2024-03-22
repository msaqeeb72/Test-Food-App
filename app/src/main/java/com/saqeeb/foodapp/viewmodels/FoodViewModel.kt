package com.saqeeb.foodapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.repos.FoodRepo
import com.saqeeb.foodapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepo: FoodRepo): ViewModel() {
    val foodList: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.foodList
    init {

    }
    fun getFoodList(){
        viewModelScope.launch {
            foodRepo.getFoodList()
        }
    }

}