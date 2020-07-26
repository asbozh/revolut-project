package com.abapplications.revolutproject

import androidx.lifecycle.*
import com.abapplications.revolutproject.network.CurrencyRates
import com.abapplications.revolutproject.network.RatesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RatesViewModel : ViewModel() {

    private val _baseCurrency = MutableLiveData<String>()

    private val _baseCurrencyValue = MutableLiveData<Double>()

    private val _currencyRates = fetchCurrencyRates().asLiveData(viewModelScope.coroutineContext)

    private fun fetchCurrencyRates() = flow {
        while (true) {
            try {
                val type = _baseCurrency.value ?: "EUR"
                emit(RatesApi.retrofitService.getProperties(type))
                delay(1000)
            } catch (e: Exception) {
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
        if ("EUR" != baseType) currencyList.add(Currency("EUR", (currentRates.rates.eur.times(baseValue))))
        if ("AUD" != baseType) currencyList.add(Currency("AUD", (currentRates.rates.aud.times(baseValue))))
        if ("BGN" != baseType) currencyList.add(Currency("BGN", (currentRates.rates.bgn.times(baseValue))))
        if ("BRL" != baseType) currencyList.add(Currency("BRL", (currentRates.rates.brl.times(baseValue))))
        if ("CAD" != baseType) currencyList.add(Currency("CAD", (currentRates.rates.cad.times(baseValue))))
        if ("CHF" != baseType) currencyList.add(Currency("CHF", (currentRates.rates.chf.times(baseValue))))
        if ("CNY" != baseType) currencyList.add(Currency("CNY", (currentRates.rates.cny.times(baseValue))))
        if ("CZK" != baseType) currencyList.add(Currency("CZK", (currentRates.rates.czk.times(baseValue))))
        if ("DKK" != baseType) currencyList.add(Currency("DKK", (currentRates.rates.dkk.times(baseValue))))
        if ("GBP" != baseType) currencyList.add(Currency("GBP", (currentRates.rates.gbp.times(baseValue))))
        if ("HKD" != baseType) currencyList.add(Currency("HKD", (currentRates.rates.hkd.times(baseValue))))
        if ("HRK" != baseType) currencyList.add(Currency("HRK", (currentRates.rates.hrk.times(baseValue))))
        if ("HUF" != baseType) currencyList.add(Currency("HUF", (currentRates.rates.huf.times(baseValue))))
        if ("IDR" != baseType) currencyList.add(Currency("IDR", (currentRates.rates.idr.times(baseValue))))
        if ("ILS" != baseType) currencyList.add(Currency("ILS", (currentRates.rates.ils.times(baseValue))))
        if ("INR" != baseType) currencyList.add(Currency("INR", (currentRates.rates.inr.times(baseValue))))
        if ("ISK" != baseType) currencyList.add(Currency("ISK", (currentRates.rates.isk.times(baseValue))))
        if ("JPY" != baseType) currencyList.add(Currency("JPY", (currentRates.rates.jpy.times(baseValue))))
        if ("KRW" != baseType) currencyList.add(Currency("KRW", (currentRates.rates.krw.times(baseValue))))
        if ("MXN" != baseType) currencyList.add(Currency("MXN", (currentRates.rates.mxn.times(baseValue))))
        if ("MYR" != baseType) currencyList.add(Currency("MYR", (currentRates.rates.myr.times(baseValue))))
        if ("NOK" != baseType) currencyList.add(Currency("NOK", (currentRates.rates.nok.times(baseValue))))
        if ("NZD" != baseType) currencyList.add(Currency("NZD", (currentRates.rates.nzd.times(baseValue))))
        if ("PHP" != baseType) currencyList.add(Currency("PHP", (currentRates.rates.php.times(baseValue))))
        if ("PLN" != baseType) currencyList.add(Currency("PLN", (currentRates.rates.pln.times(baseValue))))
        if ("RON" != baseType) currencyList.add(Currency("RON", (currentRates.rates.ron.times(baseValue))))
        if ("RUB" != baseType) currencyList.add(Currency("RUB", (currentRates.rates.rub.times(baseValue))))
        if ("SEK" != baseType) currencyList.add(Currency("SEK", (currentRates.rates.sek.times(baseValue))))
        if ("SGD" != baseType) currencyList.add(Currency("SGD", (currentRates.rates.sgd.times(baseValue))))
        if ("THB" != baseType) currencyList.add(Currency("THB", (currentRates.rates.thb.times(baseValue))))
        if ("USD" != baseType) currencyList.add(Currency("USD", (currentRates.rates.usd.times(baseValue))))
        if ("ZAR" != baseType) currencyList.add(Currency("ZAR", (currentRates.rates.zar.times(baseValue))))

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
}
