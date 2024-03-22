package com.saqeeb.foodapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        ).addMigrations(
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE food ADD COLUMN cart_qty INTEGER DEFAULT 0")
                }
            }
        ).build()
    }
}