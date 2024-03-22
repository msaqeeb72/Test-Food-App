package com.saqeeb.foodapp.apis

import com.saqeeb.foodapp.db.entities.FoodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FoodAPI {
    @GET("b/65fb371bdc74654018b5d236?meta=false")
    suspend fun getAllCategories(@Header("X-JSON-Path") jsonPath:String):Response<ArrayList<ArrayList<String>>>

    @GET("b/65fb371bdc74654018b5d236?meta=false")
    suspend fun getAllFood(@Header("X-JSON-Path") jsonPath:String):Response<ArrayList<ArrayList<FoodItem>>>
}