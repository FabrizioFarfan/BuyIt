package com.fabrizio.buyit.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.fabrizio.buyit.R
import com.fabrizio.buyit.addHeaderModel
import com.fabrizio.buyit.arch.BuyItViewModel
import com.fabrizio.buyit.database.entity.ItemWithCategoryEnity
import com.fabrizio.buyit.databinding.ModelEmptyStateBinding
import com.fabrizio.buyit.databinding.ModelItemEntityBinding
import com.fabrizio.buyit.ui.epoxy.LoadingEpoxyModel
import com.fabrizio.buyit.ui.epoxy.ViewBindingKotlinModel


class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface,
) : EpoxyController() {

    var viewState: BuyItViewModel.HomeViewState = BuyItViewModel.HomeViewState(isLoading = true)
      set(value) {
          field = value
          requestModelBuild()
      }

    override fun buildModels() {
        if (viewState.isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return

        }
        if (viewState.dataList.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        viewState.dataList.forEach { dataItem ->
            if (dataItem.isHeader) {
                addHeaderModel(dataItem.data as String)
                return@forEach
            }

            val itemWithCategoryEntity = dataItem.data as ItemWithCategoryEnity
            ItemEntityEpoxyModel(itemWithCategoryEntity, itemEntityInterface)
                .id(itemWithCategoryEntity.itemEntity.id)
                .addTo(this)
            }
    }

    data class ItemEntityEpoxyModel(
        val itemEntity: ItemWithCategoryEnity,
        val itemEntityInterface: ItemEntityInterface,
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {
        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = itemEntity.itemEntity.title

            if (itemEntity.itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = itemEntity.itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(itemEntity.itemEntity)
            }

            val colorRes = when (itemEntity.itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }

            val color = ContextCompat.getColor(root.context, colorRes)
            priorityTextView.setBackgroundColor(color)


            cardView.setStrokeColor(ColorStateList.valueOf(color))
            root.setOnClickListener {
                itemEntityInterface.onItemSelected(itemEntity.itemEntity)
            }

            categoryNameTextView.text  = itemEntity.categoryEntity?.name
        }

    }

    class EmptyStateEpoxyModel() :
        ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {
            // Nothing to do at the moment
           }
        }


}


