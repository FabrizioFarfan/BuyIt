package com.fabrizio.buyit.database.dao

import androidx.room.*
import com.fabrizio.buyit.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryEntityDao {

    @Query("SELECT * FROM category_entity")
    fun getAllCategoriesEntities(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity)

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)


}