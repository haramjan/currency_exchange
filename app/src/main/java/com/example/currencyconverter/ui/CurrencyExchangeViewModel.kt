package com.example.currencyconverter.ui

import com.example.currencyconverter.base.BaseViewModle
import com.example.currencyconverter.domain.modle.AgreegatedDomainMdl
import com.example.currencyconverter.domain.usecase.FetchCurrncyRtsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val FetchCurrncyRtsUseCase: FetchCurrncyRtsUseCase
) :
    BaseViewModle() {

    fun fetCurrencyRts(currency: String = "", amount: Double = 1.0) {
        postData(FetchCurrncyRtsUseCase.execute(currency,amount), AgreegatedDomainMdl())
    }


}