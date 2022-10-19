package com.fabrizio.buyit.arch

import com.fabrizio.buyit.database.entity.ItemEntity
import com.fabrizio.buyit.database.AppDatabase
import com.fabrizio.buyit.database.entity.CategoryEntity
import com.fabrizio.buyit.database.entity.ItemWithCategoryEnity
import kotlinx.coroutines.flow.Flow


class BuyItRepository(
    private val appDatabase: AppDatabase
) {

    // region ItemEntity
    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun updateItem(itemEntity: ItemEntity){
        appDatabase.itemEntityDao().update(itemEntity)
    }
    fun getAllItems(): Flow<List<ItemEntity>> {
        return appDatabase.itemEntityDao().getAll()
    }

    fun getAllItemWithCategoryEntities(): Flow<List<ItemWithCategoryEnity>> {
        return appDatabase.itemEntityDao().getAllItemWithCategoryEntities()
    }
    // endregion ItemEntity

    // region CategoryEntity
    suspend fun insertCategory(itemEntity: CategoryEntity) {
        appDatabase.categoryEntityDao().insertCategory(itemEntity)
    }

    suspend fun deleteCategory(itemEntity: CategoryEntity){
        appDatabase.categoryEntityDao().deleteCategory(itemEntity)
    }

    suspend fun updateCategory(itemEntity: CategoryEntity){
        appDatabase.categoryEntityDao().updateCategory(itemEntity)
    }
    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return appDatabase.categoryEntityDao().getAllCategoriesEntities()
    }
    // endregion ItemEntity



}