package com.example.maktabplus.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

interface IDao<Item, PrimaryKey> {

    fun getAll(): Flow<List<Item>> // flow to update it automatically when db changed

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(vararg items: Item): List<Long>

    @Delete
    suspend fun deleteItems(vararg items: Item): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItems(vararg items: Item): Int

    fun find(primaryKey: PrimaryKey): Flow<Item?>
}
