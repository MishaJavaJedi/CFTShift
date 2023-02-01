package com.shulga.cftshift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shulga.cftshift.util.SingleLiveEvent
import com.shulga.data.exception.EmptyResponseException
import com.shulga.data.exception.NetworkException
import com.shulga.data.exception.NotSuccessfulException
import com.shulga.domain.models.BinModel
import com.shulga.domain.models.searchHistory.SearchHistoryModel
import com.shulga.domain.usecases.DeleteCardNumberFromHistoryUseCase
import com.shulga.domain.usecases.GetCardDataUseCase
import com.shulga.domain.usecases.GetSearchHistoryUseCase
import com.shulga.domain.usecases.SaveCardNumberToSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCardDataUseCase: GetCardDataUseCase,
    private val saveCardNumberToSearchHistoryUseCase: SaveCardNumberToSearchHistoryUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val deleteCardNumberFromHistoryUseCase: DeleteCardNumberFromHistoryUseCase
) : ViewModel() {

    private val scope = viewModelScope
    private val dataFlow = MutableStateFlow<BinModel?>(null)
    val _dataFlow: StateFlow<BinModel?> get() = dataFlow
    val exceptionMessageResource = SingleLiveEvent<Int>()

    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>> = getSearchHistoryUseCase.execute()

    fun saveCardNumberToSearchHistory(cardNumber: String) {
        scope.launch(Dispatchers.IO) {
            saveCardNumberToSearchHistoryUseCase.execute(cardNumber)
        }
    }

    fun deleteCardInfoFromHistory(cardId: Int) {
        scope.launch(Dispatchers.IO) {
            deleteCardNumberFromHistoryUseCase.execute(cardId)
        }
    }

    fun getCardInfo(cardNumber: String) {
        scope.launch(Dispatchers.IO) {
            getCardDataUseCase.execute(cardNumber)
                .catch { exception ->
                    when (exception) {
                        is NotSuccessfulException -> exceptionMessageResource.postValue(R.string.not_successful)
                        is EmptyResponseException -> exceptionMessageResource.postValue(R.string.empty_response_exception)
                        is NetworkException -> exceptionMessageResource.postValue(R.string.network_exception)
                    }
                }
                .collect { result ->
                    dataFlow.value = result
                }
        }
    }
}