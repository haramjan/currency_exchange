package com.example.currencyconverter.data.local_data_source.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.HashMap

@Entity
data class ExchangesRatesDbDto(

    val ExpiryTime: Long,
    val sourceCurrency: String,
    @ColumnInfo(name = "quotes")
    //Key represents currencies short name while value is the exchange rate
    var exchangeRates: HashMap<String, Double>,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0
)