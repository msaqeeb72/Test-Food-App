package com.saqeeb.foodapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.afterTextChanged
import com.saqeeb.foodapp.databinding.FragmentFavoriteBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.ui.home.adapters.FoodListAdapter
import com.saqeeb.foodapp.utils.FoodUpdateListener
import com.saqeeb.foodapp.utils.NetworkResult
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment(),FoodUpdateListener {

    lateinit var binding:FragmentFavoriteBinding
    private val foodViewModel: FoodViewModel by activityViewModels()
    lateinit var adapter: FoodListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        adapter = FoodListAdapter(ArrayList(),this@FavoriteFragment)
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),
            RecyclerView.VERTICAL,false)
        binding.foodRecyclerView.adapter = adapter
        foodViewModel.getFavoriteFoods()
        bindStateFlow()
        bindHandlers()
        return binding.root
    }
    private fun bindHandlers() {
        binding.searchEditText.afterTextChanged {
            viewLifecycleOwner.lifecycleScope.launch {
                if(it.isEmpty()){
                    foodViewModel.getFavoriteFoods()
                } else {
                    val list = foodViewModel.searchInFavorite(it).await()
                    adapter.updateDataList(list)
                }
            }
        }
    }

    override fun foodUpdateClick(foodItem: FoodItem) {
        foodViewModel.updateFoodInDb(foodItem)
    }

    private fun bindStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {

            foodViewModel.favoriteListStateFlow.collect{
                when(it){
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Init -> {

                    }
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Success -> {
                        adapter.updateDataList(
                            it.data!!
                        )
                    }
                }
            }

        }

    }


}