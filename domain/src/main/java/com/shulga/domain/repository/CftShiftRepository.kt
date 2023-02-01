package com.shulga.domain.repository


import com.shulga.domain.models.BinModel
import com.shulga.domain.models.searchHistory.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

interface CftShiftRepository {
    suspend fun getCardDataModelAsync(cardNumber: String): Flow<BinModel>
    suspend fun saveCardNumberToSearchHistory(cardNumber: String)
    fun getSearchHistory(): Flow<List<SearchHistoryModel>>
    suspend fun deleteCardFromSearchHistory(cardId: Int)
}