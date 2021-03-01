package com.example.currencyconverter.data.local_data_source.dao


import androidx.room.*
import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import io.reactivex.*


@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies:CurrencyDbDto):Completable

    @Query("SELECT * FROM CurrencyDbDto where id=:id")
    fun get(id:Int = 0): Maybe<CurrencyDbDto>

    @Query("DELETE FROM CurrencyDbDto")
    fun clear():Completable
}