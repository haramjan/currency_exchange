package com.example.currencyconverter.data.remote_data_source.api

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.remote_data_source.dto.CurrencyNetworkDto
import io.reactivex.*
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRemoteApi {
    @GET("/list")
    fun getCurrencies(@Query("access_key") accessKey: String = BuildConfig.API_KEY):Single<CurrencyNetworkDto>
}