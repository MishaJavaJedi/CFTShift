package com.shulga.domain.usecases


import com.shulga.domain.models.BinModel
import com.shulga.domain.repository.CftShiftRepository
import kotlinx.coroutines.flow.Flow

class GetCardDataUseCase(val cftShiftRepository: CftShiftRepository) {
    suspend fun execute(cardNumber: String): Flow<BinModel> {
        return cftShiftRepository.getCardDataModelAsync(cardNumber)
    }

}