package com.shulga.domain.usecases

import com.shulga.domain.repository.CftShiftRepository

class DeleteCardNumberFromHistoryUseCase(private val cftShiftRepository: CftShiftRepository) {
    suspend fun execute(cardId: Int){
        cftShiftRepository.deleteCardFromSearchHistory(cardId)
    }
}