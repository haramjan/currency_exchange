package com.example.currencyconverter.data.local_data_source.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import io.reactivex.Completable
import io.reactivex.*


@Dao
interface ExchangeRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ExchangeRates: ExchangesRatesDbDto):Completable

    @Query("SELECT * FROM ExchangesRatesDbDto WHERE id=:id")
    fun get(id: Int = 0):Maybe<ExchangesRatesDbDto>

    @Query("DELETE FROM ExchangesRatesDbDto")
    fun clear():Completable
}