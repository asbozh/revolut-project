package com.abapplications.revolutproject

data class Currency(
    var currency: String,
    var currencyRate: Double = 0.0,
    var isBaseCurrency : Boolean = false
) {
    val currencyName: String
    get() = when (currency) {
        "EUR" -> "Euro"
        "AUD" -> "Australian Dollar"
        "BGN" -> "Bulgarian lev"
        "BRL" -> "Brazilian Real"
        "CAD" -> "Canadian Dollar"
        "CHF" -> "Swiss Franc"
        "CNY" -> "Yuan Renminbi"
        "CZK" -> "Czech Koruna"
        "DKK" -> "Danish Krone"
        "GBP" -> "Pound Sterling"
        "HKD" -> "Hong Kong Dollar"
        "HRK" -> "Kuna"
        "HUF" -> "Forint"
        "IDR" -> "Rupiah"
        "ILS" -> "New Israeli Sheqel"
        "INR" -> "Indian Rupee"
        "ISK" -> "Iceland Krona"
        "JPY" -> "Japanese Yen"
        "KRW" -> "Won"
        "MXN" -> "Mexican Peso"
        "MYR" -> "Malaysian Ringgit"
        "NOK" -> "Norwegian Krone"
        "NZD" -> "New Zealand Dollar"
        "PHP" -> "Philippine Peso"
        "PLN" -> "Zloty"
        "RON" -> "Romanian Leu"
        "RUB" -> "Russian Ruble"
        "SEK" -> "Swedish Krona"
        "SGD" -> "Singapore Dollar"
        "THB" -> "Baht"
        "USD" -> "US Dollar"
        "ZAR" -> "Rand"
        else -> "Unknown"
    }
}