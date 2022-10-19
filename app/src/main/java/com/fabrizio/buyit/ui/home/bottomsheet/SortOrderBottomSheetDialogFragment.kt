package com.fabrizio.buyit.ui.home.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.fabrizio.buyit.arch.BuyItViewModel
import com.fabrizio.buyit.databinding.BottomSheetSortOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortOrderBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private var _binding: BottomSheetSortOrderBinding? = null
    private val binding get()= _binding!!

    private val viewModel: BuyItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetSortOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = BottomSheetEpoxyController(BuyItViewModel.HomeViewState.Sort.values()){
            viewModel.currentSort = it
            dismiss()
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}