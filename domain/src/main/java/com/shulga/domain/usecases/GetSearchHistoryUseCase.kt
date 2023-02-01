package com.shulga.domain.usecases

import com.shulga.domain.models.searchHistory.SearchHistoryModel
import com.shulga.domain.repository.CftShiftRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoryUseCase(val cftShiftRepository: CftShiftRepository) {
     fun execute(): Flow<List<SearchHistoryModel>> {
        return  cftShiftRepository.getSearchHistory()
    }
}