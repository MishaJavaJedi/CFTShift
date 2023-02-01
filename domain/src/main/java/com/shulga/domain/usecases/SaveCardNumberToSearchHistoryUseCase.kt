package com.shulga.domain.usecases

import com.shulga.domain.repository.CftShiftRepository

class SaveCardNumberToSearchHistoryUseCase(val cftShiftRepository: CftShiftRepository) {
     suspend fun execute(cardNumber: String){
        cftShiftRepository.saveCardNumberToSearchHistory(cardNumber)
    }
}