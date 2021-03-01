package com.example.currencyconverter.data.remote_data_source

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.remote_data_source.dto.CurrencyNetworkDto
import com.example.currencyconverter.data.remote_data_source.dto.ExchangRateNetworkDto
import io.reactivex.*
import retrofit2.Response


interface RemoteDataService {
    fun getCurencies(): Single<CurrencyNetworkDto>
    fun getExchageRates(): Single<ExchangRateNetworkDto>

}