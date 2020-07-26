package com.abapplications.revolutproject

interface CurrencyListClickCallback {
    fun onClick(newBaseCurrency: String)
    fun onCurrencyValueChanged(newCurrencyValue: Double)
}