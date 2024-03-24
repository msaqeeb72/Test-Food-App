package com.saqeeb.foodapp.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.afterTextChanged
import com.saqeeb.foodapp.databinding.FragmentHomeBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.ui.home.adapters.CategoryAdapter
import com.saqeeb.foodapp.ui.home.adapters.FoodListAdapter
import com.saqeeb.foodapp.utils.CategoryChangeListener
import com.saqeeb.foodapp.utils.FoodItemListener
import com.saqeeb.foodapp.utils.NetworkResult
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(),FoodItemListener,CategoryChangeListener {

    private val foodViewModel:FoodViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: FoodListAdapter
    private lateinit var categoryAdapter: CategoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        adapter = FoodListAdapter(ArrayList(),this@HomeFragment,true)
        categoryAdapter = CategoryAdapter(ArrayList(),this,0)
        foodViewModel.getFoodList()
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        binding.foodRecyclerView.adapter = adapter

        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.HORIZONTAL,false)
        binding.categoryRecyclerView.adapter = categoryAdapter


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
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFoodBottomSheet)
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
        viewLifecycleOwner.lifecycleScope.launch {

            foodViewModel.categoryListStateFlow.collect{
                when(it){
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Init -> {

                    }
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Success -> {
                        categoryAdapter.updateDataList(it.data!!)
                    }
                }
            }
        }

    }

    override fun onFoodUpdate(foodItem: FoodItem) {
        foodViewModel.updateFoodInDb(foodItem)
    }

    override fun onFoodClicked(foodItem: FoodItem) {
        foodViewModel.selectedFoodItem = foodItem
        findNavController().navigate(R.id.action_homeFragment_to_foodInfoFragment)
    }

    override fun onCategoryChanged(index: Int,category:String) {
        categoryAdapter.changeSelectedPosition(index)
        if(index == 0){
            foodViewModel.getFoodList()
        }else{
            foodViewModel.getFoodByCategory(category)
        }
    }

}