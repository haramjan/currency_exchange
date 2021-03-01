package com.example.currencyconverter.data.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.local_data_source.CurrencyExchangeDatabase
import com.example.currencyconverter.data.local_data_source.LocalDataService
import com.example.currencyconverter.data.remote_data_source.RemoteDataService
import com.example.currencyconverter.data.remote_data_source.RemoteRepoImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepoProviderModule {

    @Binds
    abstract fun bindRemoteRepor(impl: RemoteRepoImp): RemoteDataService

    companion object {
        @Provides
        fun provideLocalDataService(@ApplicationContext context: Context): LocalDataService {
            return Room.databaseBuilder(
                context,
                CurrencyExchangeDatabase::class.java,
                "currency_exchanges_db"
            ).build()
        }
    }


}
