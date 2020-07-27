package com.abapplications.revolutproject.viewmodel

import androidx.lifecycle.*
import com.abapplications.revolutproject.model.Currency
import com.abapplications.revolutproject.model.CurrencyRates
import com.abapplications.revolutproject.network.RatesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyRatesViewModel : ViewModel() {

    private val _error = MutableLiveData<Boolean>(false)
    val error: LiveData<Boolean>
        get() = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _baseCurrency = MutableLiveData<String>()

    private val _baseCurrencyValue = MutableLiveData<Double>()

    private val _currencyRates = fetchCurrencyRates().asLiveData(viewModelScope.coroutineContext)

    private fun fetchCurrencyRates() = flow {
        while (true) {
            try {
                val type = _baseCurrency.value ?: "EUR"
                emit(RatesApi.retrofitService.getProperties(type))
                _errorMessage.postValue(null)
                _error.postValue(false)
                delay(1000)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                if (errorMessage.contains("No address associated with hostname", true)) {
                    _errorMessage.postValue("No Internet connection!")
                } else {
                    _errorMessage.postValue(e.message)
                }
                _error.postValue(true)
                e.printStackTrace()
            }
        }
    }

    val currencyRatesList: MediatorLiveData<MutableList<Currency>> = MediatorLiveData()

    init {
        _baseCurrency.value = "EUR"
        _baseCurrencyValue.value = 1.0

        currencyRatesList.addSource(_currencyRates) {
            viewModelScope.launch {
                val currencyList = currencyRatesList.value ?: createInitialList()
                val baseValue = _baseCurrencyValue.value ?: 1.0
                val baseType = _baseCurrency.value ?: "EUR"
                currencyRatesList.value = calculateRates(currencyList, baseValue, baseType, it)
            }
        }

        currencyRatesList.addSource(_baseCurrency) {
            viewModelScope.launch {
                val currentList = currencyRatesList.value ?: createInitialList()
                currencyRatesList.value = updateList(it, currentList)
            }
        }

        currencyRatesList.addSource(_baseCurrencyValue) {
            viewModelScope.launch {
                val currentList = currencyRatesList.value ?: createInitialList()
                val baseType = _baseCurrency.value ?: "EUR"
                val currencyRates = _currencyRates.value
                if (currencyRates != null) {
                    currencyRatesList.value = calculateRates(currentList, it, baseType, currencyRates)
                }
            }
        }

    }

    private suspend fun createInitialList(): MutableList<Currency> = withContext(Dispatchers.Default) {
        return@withContext mutableListOf(
            Currency("EUR", 1.0, true),
            Currency("AUD"),
            Currency("BGN"),
            Currency("BRL"),
            Currency("CAD"),
            Currency("CHF"),
            Currency("CNY"),
            Currency("CZK"),
            Currency("DKK"),
            Currency("GBP"),
            Currency("HKD"),
            Currency("HRK"),
            Currency("HUF"),
            Currency("IDR"),
            Currency("ILS"),
            Currency("INR"),
            Currency("ISK"),
            Currency("JPY"),
            Currency("KRW"),
            Currency("MXN"),
            Currency("MYR"),
            Currency("NOK"),
            Currency("NZD"),
            Currency("PHP"),
            Currency("PLN"),
            Currency("RON"),
            Currency("RUB"),
            Currency("SEK"),
            Currency("SGD"),
            Currency("THB"),
            Currency("USD"),
            Currency("ZAR")
        )
    }

    private suspend fun updateList(
        baseCurrencyName: String,
        currentList: MutableList<Currency>
    ): MutableList<Currency> = withContext(Dispatchers.Default) {
        val baseCurrency: Currency? = currentList.find { it.currency == baseCurrencyName }
        if (baseCurrency != null) {
            currentList[0].isBaseCurrency = false
            currentList.remove(baseCurrency)
            currentList.add(0, baseCurrency)
            currentList[0].isBaseCurrency = true
            _baseCurrencyValue.postValue(currentList[0].currencyRate)
        }
        return@withContext currentList
    }


    private suspend fun calculateRates(
        currentList: MutableList<Currency>,
        baseValue: Double,
        baseType: String,
        currentRates: CurrencyRates
    ): MutableList<Currency> = withContext(Dispatchers.Default) {
        val currencyList = currentList.filter { it.currency == baseType }.toMutableList()
        for (currencyCounter in 1 until currentList.size) {
            when (currentList[currencyCounter].currency) {
                "EUR" -> currencyList.add(Currency("EUR", (currentRates.rates.eur.times(baseValue))))
                "AUD" -> currencyList.add(Currency("AUD", (currentRates.rates.aud.times(baseValue))))
                "BGN" -> currencyList.add(Currency("BGN", (currentRates.rates.bgn.times(baseValue))))
                "BRL" -> currencyList.add(Currency("BRL", (currentRates.rates.brl.times(baseValue))))
                "CAD" -> currencyList.add(Currency("CAD", (currentRates.rates.cad.times(baseValue))))
                "CHF" -> currencyList.add(Currency("CHF", (currentRates.rates.chf.times(baseValue))))
                "CNY" -> currencyList.add(Currency("CNY", (currentRates.rates.cny.times(baseValue))))
                "CZK" -> currencyList.add(Currency("CZK", (currentRates.rates.czk.times(baseValue))))
                "DKK" -> currencyList.add(Currency("DKK", (currentRates.rates.dkk.times(baseValue))))
                "GBP" -> currencyList.add(Currency("GBP", (currentRates.rates.gbp.times(baseValue))))
                "HKD" -> currencyList.add(Currency("HKD", (currentRates.rates.hkd.times(baseValue))))
                "HRK" -> currencyList.add(Currency("HRK", (currentRates.rates.hrk.times(baseValue))))
                "HUF" -> currencyList.add(Currency("HUF", (currentRates.rates.huf.times(baseValue))))
                "IDR" -> currencyList.add(Currency("IDR", (currentRates.rates.idr.times(baseValue))))
                "ILS" -> currencyList.add(Currency("ILS", (currentRates.rates.ils.times(baseValue))))
                "INR" -> currencyList.add(Currency("INR", (currentRates.rates.inr.times(baseValue))))
                "ISK" -> currencyList.add(Currency("ISK", (currentRates.rates.isk.times(baseValue))))
                "JPY" -> currencyList.add(Currency("JPY", (currentRates.rates.jpy.times(baseValue))))
                "KRW" -> currencyList.add(Currency("KRW", (currentRates.rates.krw.times(baseValue))))
                "MXN" -> currencyList.add(Currency("MXN", (currentRates.rates.mxn.times(baseValue))))
                "MYR" -> currencyList.add(Currency("MYR", (currentRates.rates.myr.times(baseValue))))
                "NOK" -> currencyList.add(Currency("NOK", (currentRates.rates.nok.times(baseValue))))
                "NZD" -> currencyList.add(Currency("NZD", (currentRates.rates.nzd.times(baseValue))))
                "PHP" -> currencyList.add(Currency("PHP", (currentRates.rates.php.times(baseValue))))
                "PLN" -> currencyList.add(Currency("PLN", (currentRates.rates.pln.times(baseValue))))
                "RON" -> currencyList.add(Currency("RON", (currentRates.rates.ron.times(baseValue))))
                "RUB" -> currencyList.add(Currency("RUB", (currentRates.rates.rub.times(baseValue))))
                "SEK" -> currencyList.add(Currency("SEK", (currentRates.rates.sek.times(baseValue))))
                "SGD" -> currencyList.add(Currency("SGD", (currentRates.rates.sgd.times(baseValue))))
                "THB" -> currencyList.add(Currency("THB", (currentRates.rates.thb.times(baseValue))))
                "USD" -> currencyList.add(Currency("USD", (currentRates.rates.usd.times(baseValue))))
                "ZAR" -> currencyList.add(Currency("ZAR", (currentRates.rates.zar.times(baseValue))))
            }
        }
        return@withContext currencyList
    }

    fun changeBaseCurrency(newBaseCurrency: String) {
        _baseCurrency.value = newBaseCurrency
    }

    fun changeBaseCurrencyValue(newCurrencyValue: Double) {
        if (newCurrencyValue != _baseCurrencyValue.value) {
            _baseCurrencyValue.value = newCurrencyValue
        }
    }

    fun setNetworkStatus(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            _error.value = false
            _errorMessage.value = null
        } else {
            _error.value = true
            _errorMessage.value = "No Internet connection!"
        }
    }
}
