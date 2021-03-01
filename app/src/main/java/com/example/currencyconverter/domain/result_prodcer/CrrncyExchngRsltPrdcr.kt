package com.example.currencyconverter.domain.result_prodcer

import com.example.currencyconverter.base.BaseMdl
import com.example.currencyconverter.base.Result
import com.example.currencyconverter.domain.modle.AgreegatedDomainMdl
import com.example.currencyconverter.domain.modle.ExchangeRate

sealed class CurrencyExchangeLoadResult : Result() {

    data class Load(

        val sCurrency: String,
        val spprtCurrencies: HashMap<String,String>,
        val exchngeRates: List<ExchangeRate>,
        val isFirstTim:Boolean = false
    ) : CurrencyExchangeLoadResult() {
        override fun produce(state: BaseMdl): BaseMdl {

            return (state as AgreegatedDomainMdl).apply {
                isSuccess = true
                isLoading = false
                isFirstTime = isFirstTim
                sourceCurrency = sCurrency
                supportedCurrencies = spprtCurrencies
                exchangeRates = exchngeRates
            }
        }
    }
}