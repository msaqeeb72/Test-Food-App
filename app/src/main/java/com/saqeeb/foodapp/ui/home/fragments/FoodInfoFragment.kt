package com.saqeeb.foodapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.databinding.FragmentFoodInfoBinding
import com.saqeeb.foodapp.viewmodels.FoodViewModel


class FoodInfoFragment : Fragment() {


    private val viewModel:FoodViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFoodInfoBinding.inflate(layoutInflater,container,false)
        binding.foodItem = viewModel.selectedFoodItem
        binding.buttonFavorites.setOnClickListener {
            viewModel.selectedFoodItem?.isFavorite = viewModel.selectedFoodItem?.isFavorite!!.not()
            binding.buttonFavorites.setImageDrawable(ContextCompat.getDrawable(
                requireContext(),
                if (viewModel.selectedFoodItem!!.isFavorite) R.drawable.icon_favorite_filled else R.drawable.icon_favorite
            ))
            viewModel.updateFoodInDb(viewModel.selectedFoodItem!!)
        }
        binding.buttonCart.setOnClickListener {
            viewModel.selectedFoodItem?.inCart = viewModel.selectedFoodItem?.inCart!!.not()
            binding.buttonCart.setImageDrawable(ContextCompat.getDrawable(
                requireContext(),
                if (viewModel.selectedFoodItem!!.inCart) R.drawable.icon_shopping_cart_filled else R.drawable.icon_shopping_cart
            ))
            viewModel.updateFoodInDb(viewModel.selectedFoodItem!!)
        }
        binding.buttonFavorites.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (viewModel.selectedFoodItem!!.isFavorite) R.drawable.icon_favorite_filled else R.drawable.icon_favorite
            )
        )
        binding.buttonCart.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (viewModel.selectedFoodItem!!.inCart) R.drawable.icon_shopping_cart_filled else R.drawable.icon_shopping_cart
            )
        )
        return binding.root
    }


}