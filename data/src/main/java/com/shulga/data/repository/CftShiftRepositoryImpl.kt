package com.shulga.data.repository

import com.shulga.data.db.SearchHistoryDao
import com.shulga.data.db.SearchHistoryEntity
import com.shulga.data.db.toModel
import com.shulga.data.exception.EmptyResponseException
import com.shulga.data.exception.NotSuccessfulException
import com.shulga.data.network.BinCommonClient
import com.shulga.domain.models.BinModel
import com.shulga.domain.models.searchHistory.SearchHistoryModel
import com.shulga.domain.repository.CftShiftRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class CftShiftRepositoryImpl @Inject constructor(private val searchHistoryDao: SearchHistoryDao) :
    CftShiftRepository {
    override suspend fun getCardDataModelAsync(cardNumber: String): Flow<BinModel> {
        return flow {
            val response =
                BinCommonClient.retrofitService.getCardInfoByBin(cardNumber).execute()

            if (!response.isSuccessful) {
                throw NotSuccessfulException()
            }

            if (response.body() == null) {
                throw EmptyResponseException()
            }
            emit(response.body()!!)
        }
    }

    override suspend fun saveCardNumberToSearchHistory(cardNumber: String) {
        searchHistoryDao.save(SearchHistoryEntity(id = 0, number = cardNumber))
    }

    override fun getSearchHistory(): Flow<List<SearchHistoryModel>> {
        return (searchHistoryDao.getAll().map { it.map { it.toModel() } })
    }

    override suspend fun deleteCardFromSearchHistory(cardId: Int) {
        searchHistoryDao.delete(cardId)
    }
}
