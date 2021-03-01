package com.example.currencyconverter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.currencyconverter.R
import com.example.currencyconverter.base.BaseFragment
import com.example.currencyconverter.base.BaseMdl
import com.example.currencyconverter.base.BaseViewModle
import com.example.currencyconverter.databinding.FragmentCurrencyExchangeBinding
import com.example.currencyconverter.domain.modle.AgreegatedDomainMdl
import com.example.currencyconverter.domain.modle.ExchangeRate
import com.irfan.rcviewadopter.ItemLayoutManger
import com.irfan.rcviewadopter.RcAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyExchangeFragment : BaseFragment(), ItemLayoutManger,
    AdapterView.OnItemSelectedListener {


    private val viewModel: CurrencyExchangeViewModel by viewModels()

    private lateinit var binding: FragmentCurrencyExchangeBinding
    private lateinit var adapter: RcAdapter<ExchangeRate>
    private lateinit var spAdapter: ArrayAdapter<String>
    var spprtCurrencies = listOf<String>()

    override fun getLayoutId(position: Int): Int {
        return R.layout.layout_row_exchang_rate
    }

    override fun getViewModle(): BaseViewModle {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_currency_exchange, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //https://github.com/haramjan/rcadapter by Me
        spAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        )
        adapter = RcAdapter(requireContext(), this)
        adapter.bindRecyclerView(binding.rcExchangeRates)
        binding.spSupportedCurrencies.adapter = spAdapter
        listener()
        viewModel.fetCurrencyRts()

    }


    private fun listener() {
        binding.etAmount.doAfterTextChanged {
            if (!it.isNullOrEmpty())
                viewModel.fetCurrencyRts(currency, it.toString().toDouble())

        }
    }


    override fun renderView(statMdl: BaseMdl) {
        binding.pgrsBar.visibility = if (statMdl.isLoading) View.VISIBLE else View.GONE

        if (statMdl.isShowError) {
            showToast(statMdl.errorMessage)
        }

        //Todo: reduce the code and refine the logic
        if (statMdl is AgreegatedDomainMdl) {
            adapter.setItems(statMdl.exchangeRates as ArrayList<ExchangeRate>)
            if (statMdl.supportedCurrencies.isNotEmpty() && statMdl.isFirstTime) {
                spprtCurrencies = statMdl.supportedCurrencies.keys.toList()
                val supprtCurrenciesValues = statMdl.supportedCurrencies.values.toList()
                spAdapter.clear()
                spAdapter.addAll(supprtCurrenciesValues)
                binding.spSupportedCurrencies.setSelection(spprtCurrencies.indexOf(statMdl.sourceCurrency))
                binding.spSupportedCurrencies.onItemSelectedListener = this
            }
        }


    }

    var currency = "USD"
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        binding.spSupportedCurrencies
        currency = spprtCurrencies[position]
        val amount = binding.etAmount.text.toString()
        if (amount.isEmpty() || amount.toDouble() < 1.0) {
            showToast("Please enter valid amount at least 1")
            return
        }
        viewModel.fetCurrencyRts(currency, amount.toDouble())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}