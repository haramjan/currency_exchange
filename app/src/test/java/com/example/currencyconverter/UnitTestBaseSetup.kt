package com.smile.android.lite.interactors

import com.example.currencyconverter.data.remote_data_source.dto.CurrencyNetworkDto
import com.example.currencyconverter.data.remote_data_source.dto.ExchangRateNetworkDto
import com.google.gson.Gson
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.BeforeClass


/**
 * Auhtor: Irfan Khan
 */


abstract class UnitTestBaseSetup {
    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            // Tell RxAndroid to not use android main ui thread scheduler
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }

    }



    protected fun getCurrncyNetworkDto():CurrencyNetworkDto= Gson().fromJson(SUPPORTED_CURRENCIES,CurrencyNetworkDto::class.java)

    protected fun getExchngeRateNetworkDto(): ExchangRateNetworkDto =  Gson().fromJson(EXCHANGE_RATES, ExchangRateNetworkDto::class.java)


}


const val SUPPORTED_CURRENCIES =
    """
        {
    "success": true,
    "terms": "https://currencylayer.com/terms",
    "privacy": "https://currencylayer.com/privacy",
    "currencies": {
        "AED": "United Arab Emirates Dirham",
        "AFN": "Afghan Afghani",
        "ALL": "Albanian Lek",
        "AMD": "Armenian Dram",
        "ANG": "Netherlands Antillean Guilder"
    }
}  
    """
const val EXCHANGE_RATES =
    """{
    "success": true,
    "terms": "https://currencylayer.com/terms",
    "privacy": "https://currencylayer.com/privacy",
    "timestamp": 1430401802,
    "source": "USD",
    "quotes": {
        "USDAED": 3.672982,
        "USDAFN": 57.8936,
        "USDALL": 126.1652,
        "USDAMD": 475.306,
        "USDANG": 1.78952,
        "USDAOA": 109.216875,
        "USDARS": 8.901966,
        "USDAUD": 1.269072,
        "USDAWG": 1.792375,
        "USDAZN": 1.04945,
        "USDBAM": 1.757305
    }
}         
  
  """