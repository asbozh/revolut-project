package com.abapplications.revolutproject

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: MutableList<Currency>?) {
    val adapter = recyclerView.adapter as CurrencyListAdapter
    adapter.submitList(data?.toMutableList())
}

@BindingAdapter("app:srcFlagPic")
fun setFlagPic(view: AppCompatImageView, currency: String?) {
    when (currency) {
        "EUR" -> view.setImageResource(R.drawable.ic_european_union)
        "AUD" -> view.setImageResource(R.drawable.ic_australia)
        "BGN" -> view.setImageResource(R.drawable.ic_bulgaria)
        "BRL" -> view.setImageResource(R.drawable.ic_brazil)
        "CAD" -> view.setImageResource(R.drawable.ic_canada)
        "CHF" -> view.setImageResource(R.drawable.ic_switzerland)
        "CNY" -> view.setImageResource(R.drawable.ic_china)
        "CZK" -> view.setImageResource(R.drawable.ic_czech_republic)
        "DKK" -> view.setImageResource(R.drawable.ic_denmark)
        "GBP" -> view.setImageResource(R.drawable.ic_united_kingdom)
        "HKD" -> view.setImageResource(R.drawable.ic_hong_kong)
        "HRK" -> view.setImageResource(R.drawable.ic_croatia)
        "HUF" -> view.setImageResource(R.drawable.ic_hungary)
        "IDR" -> view.setImageResource(R.drawable.ic_indonesia)
        "ILS" -> view.setImageResource(R.drawable.ic_israel)
        "INR" -> view.setImageResource(R.drawable.ic_india)
        "ISK" -> view.setImageResource(R.drawable.ic_iceland)
        "JPY" -> view.setImageResource(R.drawable.ic_japan)
        "KRW" -> view.setImageResource(R.drawable.ic_south_korea)
        "MXN" -> view.setImageResource(R.drawable.ic_mexico)
        "MYR" -> view.setImageResource(R.drawable.ic_malaysia)
        "NOK" -> view.setImageResource(R.drawable.ic_norway)
        "NZD" -> view.setImageResource(R.drawable.ic_new_zealand)
        "PHP" -> view.setImageResource(R.drawable.ic_philippines)
        "PLN" -> view.setImageResource(R.drawable.ic_poland)
        "RON" -> view.setImageResource(R.drawable.ic_romania)
        "RUB" -> view.setImageResource(R.drawable.ic_russia)
        "SEK" -> view.setImageResource(R.drawable.ic_sweden)
        "SGD" -> view.setImageResource(R.drawable.ic_singapore)
        "THB" -> view.setImageResource(R.drawable.ic_thailand)
        "USD" -> view.setImageResource(R.drawable.ic_united_states_of_america)
        "ZAR" -> view.setImageResource(R.drawable.ic_south_africa)
    }
}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

@BindingAdapter("app:setCurrencyRateAsText")
fun setCurrencyRateAsText(view: TextInputEditText, currencyRateValue: Double?) {
    view.setText(currencyRateValue?.round(2).toString(), TextView.BufferType.EDITABLE)
}
