package com.fabrizio.buyit.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.fabrizio.buyit.R
import com.fabrizio.buyit.database.entity.CategoryEntity
import com.fabrizio.buyit.database.entity.ItemEntity
import com.fabrizio.buyit.databinding.FragmentAddItemEntityBinding
import com.fabrizio.buyit.ui.home.BaseFragment
import java.lang.Exception
import java.util.*


class AddItemEntityFragment: BaseFragment() {

    private var _binding : FragmentAddItemEntityBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()
    private val selectedItemEntity: ItemEntity? by lazy {
        sharedViewModel.itemWithCategoryEntitiesLiveData.value?.find {
            it.itemEntity.id == safeArgs.selectedItemEntityId
        }?.itemEntity
    }
    private var isInEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemEntityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


             binding.saveButton.setOnClickListener{
                 saveItemEntityToDatabase()
                    }

            binding.quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    val currentText = binding.titleEditText.text.toString().trim()
                    if(currentText.isEmpty()) {
                        return
                    }
                    val startIndex = currentText.indexOf("[") - 1
                    val newText = if(startIndex>0)  {
                        "${currentText.substring(0 , startIndex)} [$progress]"
                    }else  {
                        "$currentText [$progress]"
                    }

                    val sanitizedText = newText.replace(" [1]", "")
                    binding.titleEditText.setText(sanitizedText)
                    binding.titleEditText.setSelection(sanitizedText.length)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })



        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner){  event->
            event.getContent()?.let{
                if (isInEditMode){
                    navigateUp()
                    return@observe
                }
                Toast.makeText(requireActivity(), "ITEM SAVED! :D", Toast.LENGTH_SHORT).show()
                binding.titleEditText.text = null
                binding.titleEditText.requestFocus()
                mainActivity.showKeyboard()

                binding.descriptionEditText.text = null
                binding.radioGroup.check(R.id.radioButtonLow)
            }
        }

        // Setup screen if we are in edit mode
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true

            binding.titleEditText.setText(itemEntity.title)
            binding.descriptionEditText.setText(itemEntity.description)
            when (itemEntity.priority){
                1->binding.radioGroup.check(R.id.radioButtonLow)
                2->binding.radioGroup.check(R.id.radioButtonMedium)
                else->binding.radioGroup.check(R.id.radioButtonHigh)
            }

            binding.saveButton.text = "Update"
            mainActivity.supportActionBar?.title = "Update Item"

            if (itemEntity.title.contains("[")) {
                val startIndex = itemEntity.title.indexOf("[") + 1
                val endIndex = itemEntity.title.indexOf("]")

                try {
                    val progress = itemEntity.title.substring(startIndex, endIndex).toInt()
                    binding.quantitySeekBar.progress = progress
                } catch (e : Exception){

                }
            }

        }

        val categoryViewStateEpoxyController = CategoryViewStateEpoxyController{ categoryId ->
            sharedViewModel.onCategorySelected(categoryId)
        }
        binding.categoriesEpoxyController.setController(categoryViewStateEpoxyController)
        sharedViewModel.onCategorySelected(selectedItemEntity?.categoryId ?: CategoryEntity.DEFAULT_CATEGORY_ID)
        sharedViewModel.categoriesViewStateLiveData.observe(viewLifecycleOwner){  viewState ->
            categoryViewStateEpoxyController.viewState = viewState
        }




        //Show keyboard and default select our Title EditText
        mainActivity.showKeyboard()
        binding.titleEditText.requestFocus()
    }


    private fun saveItemEntityToDatabase(){
        val itemTitle = binding.titleEditText.text.toString().trim()
        if(itemTitle.isEmpty()){
            binding.titleTextField.error = "Required field!"
            return
        }

        binding.titleTextField.error= null

        val itemDescription= binding.descriptionEditText.text.toString().trim()
        val itemPriority = when(binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else-> 0
        }
        val itemCategoryId = sharedViewModel.categoriesViewStateLiveData.value?.getSelectedCategoryId() ?: return

        if (isInEditMode){
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority,
                categoryId = itemCategoryId
            )

            sharedViewModel.updateItem(itemEntity)
            return
        }

        val itemEntity = ItemEntity(
            id = UUID.randomUUID().toString(),
            title = itemTitle,
            description = itemDescription,
            priority = itemPriority,
            createdAt = System.currentTimeMillis(),
            categoryId = itemCategoryId
        )

        sharedViewModel.insertItem(itemEntity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}