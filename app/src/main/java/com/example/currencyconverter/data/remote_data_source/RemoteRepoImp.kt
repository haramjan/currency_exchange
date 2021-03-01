package com.example.currencyconverter.data.remote_data_source

import com.example.currencyconverter.data.remote_data_source.NetworkClient.RemoteApiClient
import com.example.currencyconverter.data.remote_data_source.api.CurrencyRemoteApi
import com.example.currencyconverter.data.remote_data_source.api.ExchangeRateRemoteApi
import com.example.currencyconverter.data.remote_data_source.dto.CurrencyNetworkDto
import com.example.currencyconverter.data.remote_data_source.dto.ExchangRateNetworkDto

import io.reactivex.*

import retrofit2.Response
import javax.inject.Inject

class RemoteRepoImp @Inject constructor()  : RemoteDataService {
    override fun getCurencies(): Single<CurrencyNetworkDto> {
        return RemoteApiClient.getNetworkApi(CurrencyRemoteApi::class.java)
            .getCurrencies()
    }

    override fun getExchageRates(): Single<ExchangRateNetworkDto> {
        return RemoteApiClient.getNetworkApi(ExchangeRateRemoteApi::class.java)
            .getExchageRates()
    }
}