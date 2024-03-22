package com.saqeeb.foodapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.databinding.ActivityMainBinding
import com.saqeeb.foodapp.ui.home.activities.HomeActivity
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStartedButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,HomeActivity::class.java))
        }

    }
}