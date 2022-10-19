package com.fabrizio.buyit.ui.add

import android.content.res.ColorStateList
import com.airbnb.epoxy.EpoxyController
import com.fabrizio.buyit.R
import com.fabrizio.buyit.arch.BuyItViewModel
import com.fabrizio.buyit.databinding.ModelCategoryItemSelectionBinding
import com.fabrizio.buyit.getAtrColor
import com.fabrizio.buyit.ui.epoxy.LoadingEpoxyModel
import com.fabrizio.buyit.ui.epoxy.ViewBindingKotlinModel

class CategoryViewStateEpoxyController(
    private val onCategorySelected: (String) -> Unit
): EpoxyController() {

    var viewState = BuyItViewModel.CategoriesViewState()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {

        if (viewState.isLoading){
           LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        viewState.itemList.forEach { item ->
            CategoryViewStateItem(item, onCategorySelected).id(item.categoryEntity.id).addTo(this)
        }
    }

    data class CategoryViewStateItem(
        val item: BuyItViewModel.CategoriesViewState.Item,
        private val onCategorySelected: (String) -> Unit
    ): ViewBindingKotlinModel<ModelCategoryItemSelectionBinding>(R.layout.model_category_item_selection){


        override fun ModelCategoryItemSelectionBinding.bind() {
            textView.text = item.categoryEntity.name
            root.setOnClickListener { onCategorySelected(item.categoryEntity.id)}

            val colorRes = if (item.isSelected) com.google.android.material.R.attr.colorSecondary else com.google.android.material.R.attr.colorOnSurface
            textView.setTextColor(root.getAtrColor(colorRes))
            root.setStrokeColor(ColorStateList.valueOf(colorRes))


        }

    }

}