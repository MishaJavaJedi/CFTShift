package com.shulga.cftshift.di

import com.shulga.domain.repository.CftShiftRepository
import com.shulga.domain.usecases.DeleteCardNumberFromHistoryUseCase
import com.shulga.domain.usecases.GetCardDataUseCase
import com.shulga.domain.usecases.GetSearchHistoryUseCase
import com.shulga.domain.usecases.SaveCardNumberToSearchHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetCardDataModelAsyncUseCase(CftShiftRepository: CftShiftRepository): GetCardDataUseCase {
        return GetCardDataUseCase(CftShiftRepository)
    }

    @Provides
    fun provideSaveCardNumberToSearchHistoryUseCase(CftShiftRepository: CftShiftRepository): SaveCardNumberToSearchHistoryUseCase {
        return SaveCardNumberToSearchHistoryUseCase(CftShiftRepository)
    }

    @Provides
    fun provideGetSearchHistoryUseCase(CftShiftRepository: CftShiftRepository): GetSearchHistoryUseCase {
        return GetSearchHistoryUseCase(CftShiftRepository)
    }

    @Provides
    fun provideDeleteCardNumberFromHistoryUseCase(CftShiftRepository: CftShiftRepository): DeleteCardNumberFromHistoryUseCase {
        return DeleteCardNumberFromHistoryUseCase(CftShiftRepository)
    }
}