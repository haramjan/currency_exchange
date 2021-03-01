package com.example.currencyconverter.domain.usecase

import com.example.currencyconverter.base.BaseUseCase
import com.example.currencyconverter.base.Result
import com.example.currencyconverter.base.TwoToOneMdlMapper
import com.example.currencyconverter.common.round
import com.example.currencyconverter.data.local_data_source.LocalDataService
import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import com.example.currencyconverter.data.remote_data_source.RemoteDataService
import com.example.currencyconverter.domain.modle.*
import com.example.currencyconverter.domain.result_prodcer.CurrencyExchangeLoadResult
import io.reactivex.Observable
import javax.inject.Inject

class FetchCurrncyRtsUseCase @Inject constructor(
    private val locaRepo: LocalDataService,
    private val remotRepo: RemoteDataService
) : BaseUseCase(), TwoToOneMdlMapper<CurrencyDbDto, ExchangesRatesDbDto, Result> {
    private var currency = ""
    private var amount = 1.0
    fun execute(currency: String, amount: Double): Observable<Result> {

        this.currency = currency
        this.amount = amount
        if (amount < 1.0)
            this.amount = 1.0


        val localCurrncyObrsvbl = locaRepo
            .getCurrencies()
            .toObservable()
        val remoteCurrncyObrsvbl = remotRepo
            .getCurencies().toObservable()
            .map { it.toDbDto() }
            .doOnNext { locaRepo.saveCurrencies(it).subscribe() }

        val currencyObservable =
            Observable.concat(localCurrncyObrsvbl, remoteCurrncyObrsvbl).firstElement()
                .toObservable()

        val remoteExRateobsrbl = remotRepo
            .getExchageRates().toObservable()
            .map { it.toDbDto() }
            .doOnNext { locaRepo.saveExchangeRates(it).subscribe() }
        val localExRateobsrbl = locaRepo
            .getExchangeRates().toObservable()
            .filter { it.ExpiryTime > System.currentTimeMillis() }


        val exRateObservable = Observable
            .concat(localExRateobsrbl, remoteExRateobsrbl)
            .firstElement()
            .toObservable()

        val aggregateObserver = Observable
            .zip(
                currencyObservable,
                exRateObservable,
                { supportedCurrencies, exchangeRates -> map(supportedCurrencies, exchangeRates) }
            )
        return doProcessing(aggregateObserver)
    }

    // Mapping To Domain Model
    override fun map(type1: CurrencyDbDto, type2: ExchangesRatesDbDto): Result {
        val exchangeRates = mutableListOf<ExchangeRate>()
        var isFirstTime = false
        var unitRate = 1.0

        if (currency.isNotEmpty()) {
            currency = "${type2.sourceCurrency}$currency"

            val rateForCurrency = type2.exchangeRates[currency]

            rateForCurrency?.let {
                unitRate = 1 / rateForCurrency
            }
        } else {
            currency = type2.sourceCurrency
            isFirstTime = true
        }

        type2.exchangeRates.forEach {
            val currency = it.key.replaceRange(0, type2.sourceCurrency.length, "")
            val rate = (it.value * unitRate * amount).round()
            exchangeRates.add(ExchangeRate(currency, type1.currencies[currency], rate))
        }


        return CurrencyExchangeLoadResult.Load(
            currency,
            type1.currencies,
            exchangeRates,
            isFirstTime
        )
    }


}