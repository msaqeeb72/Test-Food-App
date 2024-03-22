package com.saqeeb.foodapp.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saqeeb.foodapp.db.dao.FoodItemDao
import com.saqeeb.foodapp.db.entities.FoodItem

@Database(entities = [FoodItem::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodItemDao():FoodItemDao
}