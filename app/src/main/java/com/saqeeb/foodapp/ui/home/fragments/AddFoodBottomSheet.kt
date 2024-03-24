package com.saqeeb.foodapp.ui.home.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.saqeeb.foodapp.databinding.FragmentAddFoodBottomSheetBinding
import com.saqeeb.foodapp.viewmodels.FoodViewModel


class AddFoodBottomSheet : BottomSheetDialogFragment() {
    private val foodViewModel: FoodViewModel by activityViewModels()
    private lateinit var binding: FragmentAddFoodBottomSheetBinding

    private val imagePickResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            foodViewModel.imageUrlLiveData.postValue(it.data?.data!!.path)
            binding.foodImage.setImageURI(it.data?.data!!)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodBottomSheetBinding.inflate(layoutInflater, container, false)
        binding.viewModel = foodViewModel
        binding.lifecycleOwner = this
        binding.imageCaptureButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            imagePickResultLauncher.launch(intent)

        }
        binding.addButton.setOnClickListener {
            if (foodViewModel.isValidInput()) {
                foodViewModel.addFoodItem()
                foodViewModel.clearInputs()
                dismiss()

            }
        }
        return binding.root
    }


}