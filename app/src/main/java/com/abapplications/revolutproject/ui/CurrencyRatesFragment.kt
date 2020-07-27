package com.abapplications.revolutproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.abapplications.revolutproject.databinding.FragmentCurrencyRatesBinding
import com.abapplications.revolutproject.utils.NetworkUtils
import com.abapplications.revolutproject.viewmodel.CurrencyRatesViewModel

class CurrencyRatesFragment : Fragment(),
    CurrencyRatesListClickCallback {

    private val currencyRatesViewModel by viewModels<CurrencyRatesViewModel>()
    private lateinit var binding: FragmentCurrencyRatesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyRatesBinding.inflate(inflater, container, false).apply {
            viewModel = currencyRatesViewModel
        }
        binding.lifecycleOwner = this.viewLifecycleOwner

        val adapter =
            CurrencyListAdapter(this)
        binding.rvCurrencyList.adapter = adapter
        adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                binding.rvCurrencyList.smoothScrollToPosition(0)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        currencyRatesViewModel.setNetworkStatus(NetworkUtils.isNetworkAvailable(requireContext()))
    }

    override fun onClick(newBaseCurrency: String) {
        currencyRatesViewModel.changeBaseCurrency(newBaseCurrency)
    }

    override fun onCurrencyValueChanged(newCurrencyValue: Double) {
        currencyRatesViewModel.changeBaseCurrencyValue(newCurrencyValue)
    }
}