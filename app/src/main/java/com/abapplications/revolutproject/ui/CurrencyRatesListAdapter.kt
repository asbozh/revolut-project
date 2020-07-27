package com.abapplications.revolutproject.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abapplications.revolutproject.R
import com.abapplications.revolutproject.databinding.CurrencyRateListItemBinding
import com.abapplications.revolutproject.model.Currency
import java.lang.Exception

class CurrencyListAdapter(private val currencyRatesListClickCallback: CurrencyRatesListClickCallback) :
    ListAdapter<Currency, CurrencyListAdapter.CurrencyListViewHolder>(
        CurrencyDiffCallback()
    ) {

    private val isBaseCurrency = 1
    private val isNotBaseCurrency = 2

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyListViewHolder {
        val binding: CurrencyRateListItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.currency_rate_list_item,
                parent,
                false
            )
        binding.callback = currencyRatesListClickCallback
        return CurrencyListViewHolder(
            binding
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isBaseCurrency) {
            isBaseCurrency
        } else {
            isNotBaseCurrency
        }
    }

    override fun onBindViewHolder(
        holder: CurrencyListViewHolder,
        position: Int
    ) {
        if (getItemViewType(position) == isBaseCurrency) {
            holder.binding.etMoneyAmount.isFocusableInTouchMode = true
            holder.binding.etMoneyAmount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(input: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var newCurrencyValue = 0.0
                    if (input != null && input.isNotBlank()) {
                        try {
                            newCurrencyValue = input.toString().toDouble()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    currencyRatesListClickCallback.onCurrencyValueChanged(newCurrencyValue)
                }
            })
        } else {
            holder.binding.etMoneyAmount.isFocusableInTouchMode = false
        }
        holder.binding.currency = getItem(position)
        holder.binding.executePendingBindings()
    }

    class CurrencyListViewHolder(val binding: CurrencyRateListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}

class CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.currency == newItem.currency

    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}