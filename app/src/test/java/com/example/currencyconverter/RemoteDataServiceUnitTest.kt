package com.example.currencyconverter

import com.example.currencyconverter.data.remote_data_source.RemoteRepoImp
import com.smile.android.lite.interactors.UnitTestBaseSetup
import com.smile.android.lite.interactors.EXCHANGE_RATES
import com.smile.android.lite.interactors.SUPPORTED_CURRENCIES
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test



class RemoteDataServiceUnitTest : UnitTestBaseSetup() {
    private val server = MockWebServer()

    @Before
    fun beforeEachTes() {
        // Perform testing on staging
        server.start(BuildConfig.port)
    }


    @After
    fun afterEachTes() {
        server.shutdown()
    }


    @Test
    fun `Currencies json should be parsed correctly`() {
        val response = MockResponse()
            .setBody(SUPPORTED_CURRENCIES)
            .setResponseCode(200)
         server.enqueue(response)

        val expected = getCurrncyNetworkDto()

         val retrofitClient = RemoteRepoImp()
        val testObsrvr = retrofitClient.getCurencies().test()
        testObsrvr.hasSubscription()
        testObsrvr.assertComplete()
        testObsrvr.assertNoErrors()
        testObsrvr.assertValue {
           val actual =  it
            actual == expected
        }
    }

    @Test
    fun `Exchange rates json should be parsed correctly`() {
        val response = MockResponse()
            .setBody(EXCHANGE_RATES)
            .setResponseCode(200)
         server.enqueue(response)

        val expected = getExchngeRateNetworkDto()

         val retrofitClient = RemoteRepoImp()
        val testObsrvr = retrofitClient.getExchageRates().test()
        testObsrvr.hasSubscription()
        testObsrvr.assertComplete()


        testObsrvr.assertValue {
          it == expected
        }
    }

}


