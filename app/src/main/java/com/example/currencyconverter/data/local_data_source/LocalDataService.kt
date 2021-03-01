package com.example.currencyconverter.data.local_data_source

import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import io.reactivex.*

interface LocalDataService {
    fun saveCurrencies(dbDtos: CurrencyDbDto):Completable
    fun saveExchangeRates(exchangeRates: ExchangesRatesDbDto):Completable
    fun getCurrencies(): Maybe<CurrencyDbDto>
    fun getExchangeRates(): Maybe<ExchangesRatesDbDto>
}