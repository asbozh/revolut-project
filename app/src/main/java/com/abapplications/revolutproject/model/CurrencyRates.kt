package com.abapplications.revolutproject.model


import com.squareup.moshi.Json

data class Rates(@Json(name = "CHF")
                 val chf: Double = 0.0,
                 @Json(name = "HRK")
                 val hrk: Double = 0.0,
                 @Json(name = "EUR")
                 val eur: Double = 0.0,
                 @Json(name = "MXN")
                 val mxn: Double = 0.0,
                 @Json(name = "ZAR")
                 val zar: Double = 0.0,
                 @Json(name = "INR")
                 val inr: Double = 0.0,
                 @Json(name = "CNY")
                 val cny: Double = 0.0,
                 @Json(name = "THB")
                 val thb: Double = 0.0,
                 @Json(name = "AUD")
                 val aud: Double = 0.0,
                 @Json(name = "ILS")
                 val ils: Double = 0.0,
                 @Json(name = "KRW")
                 val krw: Double = 0.0,
                 @Json(name = "JPY")
                 val jpy: Double = 0.0,
                 @Json(name = "PLN")
                 val pln: Double = 0.0,
                 @Json(name = "GBP")
                 val gbp: Double = 0.0,
                 @Json(name = "IDR")
                 val idr: Double = 0.0,
                 @Json(name = "HUF")
                 val huf: Double = 0.0,
                 @Json(name = "PHP")
                 val php: Double = 0.0,
                 @Json(name = "RUB")
                 val rub: Double = 0.0,
                 @Json(name = "HKD")
                 val hkd: Double = 0.0,
                 @Json(name = "ISK")
                 val isk: Double = 0.0,
                 @Json(name = "DKK")
                 val dkk: Double = 0.0,
                 @Json(name = "CAD")
                 val cad: Double = 0.0,
                 @Json(name = "MYR")
                 val myr: Double = 0.0,
                 @Json(name = "USD")
                 val usd: Double = 0.0,
                 @Json(name = "BGN")
                 var bgn: Double = 0.0,
                 @Json(name = "NOK")
                 val nok: Double = 0.0,
                 @Json(name = "RON")
                 val ron: Double = 0.0,
                 @Json(name = "SGD")
                 val sgd: Double = 0.0,
                 @Json(name = "CZK")
                 val czk: Double = 0.0,
                 @Json(name = "SEK")
                 val sek: Double = 0.0,
                 @Json(name = "NZD")
                 val nzd: Double = 0.0,
                 @Json(name = "BRL")
                 val brl: Double = 0.0)

data class CurrencyRates(@Json(name = "rates")
                         val rates: Rates,
                         @Json(name = "baseCurrency")
                         val baseCurrency: String = "")

