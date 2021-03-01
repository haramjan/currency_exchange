package com.example.currencyconverter.data.local_data_source.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyDbDto(

    val currencies: HashMap<String, String>,
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0
)