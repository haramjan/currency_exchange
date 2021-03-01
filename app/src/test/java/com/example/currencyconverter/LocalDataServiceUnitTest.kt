package com.example.currencyconverter

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencyconverter.data.local_data_source.CurrencyExchangeDatabase
import com.example.currencyconverter.data.local_data_source.entities.CurrencyDbDto
import com.example.currencyconverter.data.local_data_source.entities.ExchangesRatesDbDto
import com.google.common.truth.Truth
import com.smile.android.lite.interactors.UnitTestBaseSetup
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LocalDataServiceUnitTest : UnitTestBaseSetup() {
    private lateinit var db: CurrencyExchangeDatabase
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CurrencyExchangeDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Currencies should be saved correctly`() {
        val expected = CurrencyDbDto(HashMap<String, String>().apply {
            put("AED", "United Arab Emirates Dirham")
            put("AFN", "Afghan Afghani")
        },0)


        db.saveCurrencies(expected).test().assertComplete()

        val testObservable = db.getCurrencies().test()
        testObservable.assertNoErrors()

        val actual = testObservable.values()
        Truth.assertWithMessage("Result is empty").that(actual.size).isGreaterThan(0)
        Truth.assertWithMessage("Currencies are not Inserted correctly")
            .that(actual[0].currencies.keys).isEqualTo(expected.currencies.keys)
    }


    @Test
    fun `Exchange rates should be saved correctly`() {
        val expected = ExchangesRatesDbDto(1000000L, "USD", HashMap<String, Double>().apply {
            put("AED", 0.23)
            put("AFN", 0.33)
        },0)


        db.saveExchangeRates(expected).test().assertComplete()

        val testObservable = db.getExchangeRates().test()
        testObservable.assertNoErrors()

        val actual = testObservable.values()
        Truth.assertWithMessage("Result is empty").that(actual.size).isGreaterThan(0)

        Truth.assertWithMessage("wrong source currency ")
            .that(actual[0].sourceCurrency).isEqualTo(expected.sourceCurrency)

        Truth.assertWithMessage("Wrong exchange rates")
            .that(actual[0].exchangeRates.keys).isEqualTo(expected.exchangeRates.keys)
    }
}