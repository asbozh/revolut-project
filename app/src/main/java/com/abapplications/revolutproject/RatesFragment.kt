package com.abapplications.revolutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.abapplications.revolutproject.databinding.FragmentRatesBinding


class RatesFragment : Fragment(), CurrencyListClickCallback {

    private val ratesViewModel by viewModels<RatesViewModel>()
    private lateinit var binding: FragmentRatesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatesBinding.inflate(inflater, container, false).apply {
            viewModel = ratesViewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner

        val adapter = CurrencyListAdapter(this)
        binding.rvCurrencyList.adapter = adapter
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                binding.rvCurrencyList.smoothScrollToPosition(0)
            }
        })

        return binding.root
    }

    override fun onClick(newBaseCurrency: String) {
        ratesViewModel.changeBaseCurrency(newBaseCurrency)
    }

    override fun onCurrencyValueChanged(newCurrencyValue: Double) {
        ratesViewModel.changeBaseCurrencyValue(newCurrencyValue)
    }
}