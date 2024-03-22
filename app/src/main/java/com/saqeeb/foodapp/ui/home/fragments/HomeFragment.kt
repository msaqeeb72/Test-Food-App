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
import com.saqeeb.foodapp.afterTextChanged
import com.saqeeb.foodapp.databinding.FragmentHomeBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.ui.home.adapters.FoodListAdapter
import com.saqeeb.foodapp.utils.FoodUpdateListener
import com.saqeeb.foodapp.utils.NetworkResult
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(),FoodUpdateListener {

    private val foodViewModel:FoodViewModel by activityViewModels()
    lateinit var adapter: FoodListAdapter
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        adapter = FoodListAdapter(ArrayList(),this@HomeFragment)
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        binding.foodRecyclerView.adapter = adapter
        foodViewModel.getFoodList()
        bindHandlers()
        bindStateFlow()
        return binding.root
    }

    private fun bindHandlers() {
        binding.searchEditText.afterTextChanged {
            viewLifecycleOwner.lifecycleScope.launch {
                if(it.isEmpty()){
                    foodViewModel.getFoodList()
                } else {
                    val list = foodViewModel.searchFoodInDb(it).await()
                    adapter.updateDataList(list)
                }
            }
        }
    }

    private fun bindStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {

            foodViewModel.foodListStateFlow.collect{
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

    override fun foodUpdateClick(foodItem: FoodItem) {
        foodViewModel.updateFoodInDb(foodItem)
    }

}