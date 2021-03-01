package com.example.currencyconverter.data.remote_data_source.api



import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.remote_data_source.dto.ExchangRateNetworkDto
import io.reactivex.*
import retrofit2.Response
import retrofit2.http.*

interface ExchangeRateRemoteApi {
    @GET("/live")
    fun getExchageRates(@Query("access_key") accessKey: String = BuildConfig.API_KEY): Single<ExchangRateNetworkDto>
}