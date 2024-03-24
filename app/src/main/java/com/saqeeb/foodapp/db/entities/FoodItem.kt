package com.saqeeb.foodapp.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "food")
data class FoodItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int,

    val category: String,

    val distance: Double,

    @SerializedName("food_image")
    @ColumnInfo("food_image")
    val foodImage: String,

    @SerializedName("food_name")
    @ColumnInfo("food_name")
    val foodName: String,

    val ingredients: String,

    val rating: Double,

    @SerializedName("reviews_count")
    @ColumnInfo("reviews_count")
    val reviewsCount: Int,

    val tag: String,

    @SerializedName("time_taken")
    @ColumnInfo("time_taken")
    val timeTaken: Int,

    @SerializedName("cart_qty")
    @ColumnInfo("cart_qty")
    var cartQty: Int,

    @SerializedName("is_favorite")
    @ColumnInfo("is_favorite")
    var isFavorite:Boolean,

    @SerializedName("in_cart")
    @ColumnInfo("in_cart")
    var inCart:Boolean
)