package com.shulga.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract fun getSearchHistoryDao(): SearchHistoryDao
}