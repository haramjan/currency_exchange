package com.example.currencyconverter.data.remote_data_source.dto

import android.text.BoringLayout
import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import com.google.gson.annotations.SerializedName

data class CurrencyNetworkDto (

   @SerializedName("success")
    val isSuccess:Boolean,

    @SerializedName("terms")
    val termsLink:String,

    @SerializedName("privacy")
    val privacyLink:String,

    @SerializedName("currencies")
    val supportedCurrencies:HashMap<String,String>
){
 fun toDbDto():CurrencyDbDto{
  return CurrencyDbDto(supportedCurrencies)
 }
}