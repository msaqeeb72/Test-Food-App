package com.saqeeb.foodapp.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saqeeb.foodapp.R
import com.saqeeb.foodapp.databinding.FragmentCartBinding
import com.saqeeb.foodapp.db.entities.FoodItem
import com.saqeeb.foodapp.ui.home.adapters.CartListAdapter
import com.saqeeb.foodapp.utils.FoodItemListener
import com.saqeeb.foodapp.utils.NetworkResult
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import kotlinx.coroutines.launch

class CartFragment : Fragment(), FoodItemListener {


    lateinit var binding: FragmentCartBinding
    private val foodViewModel: FoodViewModel by activityViewModels()
    lateinit var adapter: CartListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(layoutInflater)

        adapter = CartListAdapter(ArrayList(), this@CartFragment)
        foodViewModel.getCartList()
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL, false
        )
        binding.cartRecyclerView.adapter = adapter

        bindStateFlow()
        return binding.root
    }

    private fun bindStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {

            foodViewModel.cartListStateFlow.collect{
                when(it){
                    is NetworkResult.Success -> {
                        adapter.updateDataList(
                            it.data!!
                        )
                    }
                    else -> {
                        //TODO
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
        findNavController().navigate(R.id.action_cartFragment_to_foodInfoFragment)
    }

}