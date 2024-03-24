package com.saqeeb.foodapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.saqeeb.foodapp.databinding.ActivityMainBinding
import com.saqeeb.foodapp.throttleClicks
import com.saqeeb.foodapp.ui.home.activities.HomeActivity
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:FoodViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStartedButton.setOnClickListener {
            binding.progressBar.isVisible = true
            it.visibility = View.INVISIBLE
            it.throttleClicks()
            CoroutineScope(Dispatchers.IO).launch {
                 viewModel.getFoodFromAPIAndStoreInDb()

                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                    }

            }
        }

    }
}