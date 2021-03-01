package com.example.currencyconverter.data.remote_data_source.NetworkClient

import com.example.currencyconverter.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RemoteApiClient {
    private var retrofitInstance: Retrofit? = null

    @JvmStatic
    fun <T> getNetworkApi(clazz: Class<T>): T {
        return getRetrofit().create(clazz)
    }

    private fun getRetrofit(): Retrofit {
        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        return retrofitInstance!!
    }

    private fun getHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

            okHttpClient.addInterceptor(loggingInterceptor)
        }

        return okHttpClient.build()

    }

}