package com.fabrizio.buyit.database.dao

import androidx.room.*
import com.fabrizio.buyit.database.entity.ItemEntity
import com.fabrizio.buyit.database.entity.ItemWithCategoryEnity
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemEntityDao {


    @Query("SELECT * FROM item_entity")
    fun getAll(): Flow<List<ItemEntity>>

    @Transaction
    @Query("SELECT * FROM item_entity")
    fun getAllItemWithCategoryEntities(): Flow<List<ItemWithCategoryEnity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ItemEntity: ItemEntity)

    @Delete
    suspend fun delete(ItemEntity: ItemEntity)

    @Update
    suspend fun update(ItemEntity: ItemEntity)


}