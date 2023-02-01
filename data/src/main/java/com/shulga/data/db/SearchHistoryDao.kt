package com.shulga.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM searchHistory")
    fun getAll(): Flow<List<SearchHistoryEntity>>

    @Query("DELETE FROM searchHistory WHERE id = :cardId")
    suspend fun delete(cardId: Int)
}