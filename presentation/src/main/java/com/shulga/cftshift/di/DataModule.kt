package com.shulga.cftshift.di

import android.content.Context
import androidx.room.Room
import com.shulga.data.db.AppDb
import com.shulga.data.db.SearchHistoryDao
import com.shulga.data.repository.CftShiftRepositoryImpl
import com.shulga.domain.repository.CftShiftRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCftShiftRepository(searchHistoryDao: SearchHistoryDao): CftShiftRepository {
        return CftShiftRepositoryImpl(searchHistoryDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app, AppDb::class.java,
        "searchHistory.db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSearchHistoryDao(db: AppDb) = db.getSearchHistoryDao()
}