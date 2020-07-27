package com.abapplications.revolutproject.ui

interface CurrencyRatesListClickCallback {
    fun onClick(newBaseCurrency: String)
    fun onCurrencyValueChanged(newCurrencyValue: Double)
}