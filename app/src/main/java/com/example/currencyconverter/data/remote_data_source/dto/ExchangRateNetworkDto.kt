package com.example.currencyconverter.data.remote_data_source.dto

import android.provider.SyncStateContract
import com.example.currencyconverter.common.Constants
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import com.google.gson.annotations.SerializedName
import java.util.concurrent.atomic.AtomicStampedReference

data class ExchangRateNetworkDto(

    @SerializedName("success")
    val isSuccess: Boolean,

    @SerializedName("terms")
    val termsLink: String,

    @SerializedName("timestamp")
    val timeStamp: Long,

    @SerializedName("privacy")
    val privacyLink: String,

    @SerializedName("source")
    val sourceCurrency: String,

    @SerializedName("quotes")
    val exchangeRates: HashMap<String, Double>
) {
    fun toDbDto(): ExchangesRatesDbDto {
        return ExchangesRatesDbDto(System.currentTimeMillis()+Constants.LOAD_INTERVAL, sourceCurrency, exchangeRates)
    }
}