package com.fabrizio.buyit.ui.home.bottomsheet

import com.airbnb.epoxy.EpoxyController
import com.fabrizio.buyit.R
import com.fabrizio.buyit.arch.BuyItViewModel
import com.fabrizio.buyit.databinding.ModelSortOrderItemBinding
import com.fabrizio.buyit.ui.epoxy.ViewBindingKotlinModel

class BottomSheetEpoxyController(
    private val sortOptions: Array<BuyItViewModel.HomeViewState.Sort>,
    private val selectedCallback: (BuyItViewModel.HomeViewState.Sort) -> Unit
): EpoxyController() {
    override fun buildModels() {
        sortOptions.forEach {
            SortOrderItemEpoxyModel(it, selectedCallback).id(it.displayName).addTo(this)
        }
    }

    data class SortOrderItemEpoxyModel(
        val sort: BuyItViewModel.HomeViewState.Sort,
        val selectedCallback: (BuyItViewModel.HomeViewState.Sort) -> Unit
    ): ViewBindingKotlinModel<ModelSortOrderItemBinding>(R.layout.model_sort_order_item) {

        override fun ModelSortOrderItemBinding.bind() {
            textView.text = sort.displayName
            root.setOnClickListener { selectedCallback(sort) }
        }
    }
}