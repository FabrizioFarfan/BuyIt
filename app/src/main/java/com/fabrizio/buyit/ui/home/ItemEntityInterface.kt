package com.fabrizio.buyit.ui.home

import com.fabrizio.buyit.database.entity.ItemEntity


interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onItemSelected(itemEntity: ItemEntity)
}