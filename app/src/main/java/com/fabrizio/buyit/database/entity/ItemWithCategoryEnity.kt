package com.fabrizio.buyit.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithCategoryEnity(
    @Embedded
    val itemEntity: ItemEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val categoryEntity: CategoryEntity?
    )