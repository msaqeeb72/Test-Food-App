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
import com.saqeeb.foodapp.databinding.FragmentHomeBinding
import com.saqeeb.foodapp.ui.home.adapters.FoodListAdapter
import com.saqeeb.foodapp.utils.NetworkResult
import com.saqeeb.foodapp.viewmodels.FoodViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val foodViewModel:FoodViewModel by activityViewModels()
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        foodViewModel.getFoodList()
        bindStateFlow()
        return binding.root
    }

    private fun bindStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {

            foodViewModel.foodList.collect{
                when(it){
                    is NetworkResult.Error -> {

                    }
                    is NetworkResult.Init -> {

                    }
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Success -> {
                        val adapter = FoodListAdapter(it.data!!)
                        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                        binding.foodRecyclerView.adapter = adapter

                    }
                }
            }

        }

    }

}