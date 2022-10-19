package com.fabrizio.buyit.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabrizio.buyit.R
import com.fabrizio.buyit.database.entity.CategoryEntity
import com.fabrizio.buyit.databinding.FragmentAddCategoryBinding
import com.fabrizio.buyit.ui.home.BaseFragment
import java.util.*

class AddCategoryFragment: BaseFragment() {

    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryNameEditText.requestFocus()
        mainActivity.showKeyboard()
        binding.saveButton.setOnClickListener {
            saveCategoryToDataBase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { event ->
            event.getContent()?.let {
                 navigateUp()
            }
        }
    }


    private fun saveCategoryToDataBase(){
        val categoryName = binding.categoryNameEditText.text.toString().trim()
        if (categoryName.isEmpty()) {
            binding.categoryNameTextField.error = "* Required field"
            return
        }

        val categoryEntity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        sharedViewModel.insertCategory(categoryEntity)
    }




    override fun onDestroyView() {
        super.onDestroyView()
    }
}