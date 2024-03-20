package com.saqeeb.foodapp.di

import android.content.Context
import androidx.room.Room
import com.saqeeb.foodapp.db.AppDatabase
import com.saqeeb.foodapp.db.dao.FoodItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFoodDao(appDatabase: AppDatabase): FoodItemDao {
        return appDatabase.foodItemDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "food_database"
        ).build()
    }
}