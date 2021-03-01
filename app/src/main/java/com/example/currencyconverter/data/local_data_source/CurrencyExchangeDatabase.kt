package com.example.currencyconverter.data.local_data_source

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.data.local_data_source.dao.CurrencyDao
import com.example.currencyconverter.data.local_data_source.dao.ExchangeRatesDao
import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import io.reactivex.*


@Database(
    entities = [CurrencyDbDto::class, ExchangesRatesDbDto::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MapTypeConverter::class)
abstract class CurrencyExchangeDatabase() : RoomDatabase(), LocalDataService {
    protected abstract fun getCurrenyDao(): CurrencyDao
    protected abstract fun getExchangeRatesDao(): ExchangeRatesDao

    override fun saveCurrencies(dbDtos: CurrencyDbDto): Completable {

        return getCurrenyDao().insert(dbDtos)
            .doOnComplete {
                Log.d("Insert","Currency inserted")
            }
    }

    override fun saveExchangeRates(exchangeRates: ExchangesRatesDbDto): Completable {

        return getExchangeRatesDao().insert(exchangeRates)
            .doOnComplete {
                Log.d("Insert","ExchangeRate is  inserted")
            }
    }

    override fun getCurrencies(): Maybe<CurrencyDbDto> {
        return getCurrenyDao().get()
            .doOnSuccess {
                Log.d("select","currency is retrieved")
            }

    }

    override fun getExchangeRates(): Maybe<ExchangesRatesDbDto> {
          return getExchangeRatesDao().get()
              .doOnSuccess {
                  Log.d("select","exchanges rate is retrieved")
              }
    }

}