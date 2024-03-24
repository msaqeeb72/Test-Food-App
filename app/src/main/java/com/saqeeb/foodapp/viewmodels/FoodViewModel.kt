package com.saqeeb.foodapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.repos.FoodRepo
import com.saqeeb.foodapp.roundToTwoDecimalPoints
import com.saqeeb.foodapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FoodViewModel @Inject constructor(private val foodRepo: FoodRepo) : ViewModel() {

    val foodListStateFlow: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.foodList
    val favoriteListStateFlow: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.favoriteList
    val cartListStateFlow: StateFlow<NetworkResult<ArrayList<FoodItem>>>
        get() = foodRepo.cartListFlow
    val categoryListStateFlow: StateFlow<NetworkResult<ArrayList<String>>>
        get() = foodRepo.categoriesListFlow

    val foodNameLiveData = MutableLiveData("")
    val ingredientsLiveData = MutableLiveData("")
    val imageUrlLiveData = MutableLiveData("")
    init {
        foodRepo.getCategoriesList();
    }
    var selectedFoodItem:FoodItem? = null
    suspend fun getFoodFromAPIAndStoreInDb(){
        foodRepo.getFoodListAndStoreInDb()
    }

    fun getFoodList() {
        viewModelScope.launch {
            foodRepo.getFoodListFromDb()
        }
    }fun getFoodByCategory(category:String) {
        viewModelScope.launch {
            foodRepo.getFoodByCategoryFromDb(category)
        }
    }

    fun updateFoodInDb(foodItem: FoodItem) {
        viewModelScope.launch {
            foodRepo.updateFoodItem(foodItem)
        }
    }

    fun searchFoodInDb(text: String): Deferred<ArrayList<FoodItem>> {
        return viewModelScope.async {
            foodRepo.searchFoodInDb(text)
        }
    }

    fun searchInFavorite(text: String): Deferred<ArrayList<FoodItem>> {
        return viewModelScope.async {
            foodRepo.searchInFavorite(text)
        }
    }

    fun getFavoriteFoods() {
        viewModelScope.launch {
            foodRepo.getFavoriteFoods()
        }
    }

    fun getCartList() {
        viewModelScope.launch {
            foodRepo.getCartList()
        }

    }
    fun addFoodItem() {
        viewModelScope.launch {
            foodRepo.addFoodItem(
                FoodItem(
                    id = 0,
                    category = "All",
                    distance =  Random.nextDouble(1.0,12.0).roundToTwoDecimalPoints(),
                    foodImage = imageUrlLiveData.value?:"",
                    ingredients = ingredientsLiveData.value!!,
                    rating = Random.nextDouble(1.0,5.0).roundToTwoDecimalPoints(),
                    tag = "Custom",
                    cartQty = 0,
                    foodName = foodNameLiveData.value!!,
                    timeTaken = Random.nextInt(1,25),
                    inCart = false,
                    isFavorite = false, reviewsCount = Random.nextInt(50,500)

                )
            )
            foodRepo.getFoodListFromDb()
        }

    }
    fun isValidInput():Boolean{
        return (foodNameLiveData.value?.isNotEmpty()!! && ingredientsLiveData.value?.isNotEmpty()!! && imageUrlLiveData.value?.isNotEmpty()!!)
    }

    fun clearInputs() {
        foodNameLiveData.postValue("")
        ingredientsLiveData.postValue("")
        imageUrlLiveData.postValue("")
    }
}