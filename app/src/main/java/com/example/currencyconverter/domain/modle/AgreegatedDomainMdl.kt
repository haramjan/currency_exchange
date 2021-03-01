package com.example.currencyconverter.domain.modle

import com.example.currencyconverter.base.BaseMdl

data class AgreegatedDomainMdl(
    var isFirstTime: Boolean = false,
    var sourceCurrency: String = "",
    var supportedCurrencies: HashMap<String,String> = HashMap(),
    var exchangeRates: List<ExchangeRate> = mutableListOf()
) : BaseMdl()

data class ExchangeRate(val currency: String, val discrp:String?, val exchangeRate: Double)