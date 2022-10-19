package com.fabrizio.buyit.ui.epoxy.models

import com.fabrizio.buyit.R
import com.fabrizio.buyit.databinding.ModelHeaderItemBinding
import com.fabrizio.buyit.ui.epoxy.ViewBindingKotlinModel


data class HeaderEpoxyModel(
    val headerText: String
): ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {
    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}