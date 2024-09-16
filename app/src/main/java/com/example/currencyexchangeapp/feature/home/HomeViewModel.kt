package com.example.currencyexchangeapp.feature.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangeapp.data.interactor.CurrencyExchangeInteractor
import com.example.currencyexchangeapp.data.local.db.entity.Currency
import com.example.currencyexchangeapp.data.local.db.entity.Exchange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

class HomeViewModel(
    private val interactor: CurrencyExchangeInteractor
): ViewModel(), CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.Main


    private val _errorStateFlow = MutableStateFlow<Throwable?>(null)
    private val errorStateFlow: StateFlow<Throwable?> = _errorStateFlow
    val errorState = errorStateFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    private val _infoDialogMessage = MutableStateFlow<String?>(null)
    val infoDialogMessage: StateFlow<String?> = _infoDialogMessage

    val observeExchangeCountFlow: StateFlow<Int> = interactor.observeExchangeCount()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val observeCurrenciesFlow: StateFlow<List<Currency>> = interactor.observeCurrencies()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val observeCurrenciesWithBalanceFlow: StateFlow<List<Currency>> = interactor.observeCurrenciesWithBalance()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    init {
        setupData()
    }

    private fun setupData() {
        handleErrors {
            loadAsync { interactor.setupData() }
        }
    }

    fun updateCurrencyBalances(currencies: List<Currency>) {
        handleErrors {
            loadAsync { interactor.updateCurrencyBalances(currencies) }
        }
    }

    fun updateExchange(count: Int) {
        handleErrors {
            loadAsync { interactor.updateExchange(count) }
        }
    }

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    private suspend fun inMain(block: suspend () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }

    private fun <T> handleErrors(
        mute: Boolean = false, block: suspend () -> T
    ) = viewModelScope.launch {
        try {
            block()
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            if (!mute && throwable !is CancellationException) {
                onNewError(throwable)
            }
        }
    }


    private suspend fun <T> loadAsync(block: suspend () -> T): T? {
        try {
            return withContext(SupervisorJob() + IO) {
                block()
            }
        } catch (throwable: Exception) {
            Timber.e(throwable)
            throw throwable
        }
    }


    private fun onNewError(throwable: Throwable) {
        _errorStateFlow.tryEmit(throwable)
    }

    fun onErrorHandled() {
        _errorStateFlow.tryEmit(null)
    }

    fun onToastShown() {
        _toastMessage.tryEmit(null)
    }

    fun onNewToast(msg: String) {
        _toastMessage.value = msg
    }

    fun onNewInfoDialog(msg: String) {
        _infoDialogMessage.value = msg
    }

    fun onInfoDialogShown() {
        _infoDialogMessage.value = null
    }
}