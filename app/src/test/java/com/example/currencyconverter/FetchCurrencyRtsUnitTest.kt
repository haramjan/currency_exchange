package com.example.currencyconverter

import com.example.currencyconverter.data.local_data_source.LocalDataService
import com.example.currencyconverter.data.remote_data_source.RemoteDataService
import com.example.currencyconverter.domain.modle.AgreegatedDomainMdl
import com.example.currencyconverter.domain.usecase.FetchCurrncyRtsUseCase
import com.google.common.truth.Truth
import com.smile.android.lite.interactors.UnitTestBaseSetup
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.junit.Before
import org.junit.Test


class FetchCurrencyRtsUnitTest : UnitTestBaseSetup() {
    @MockK
    lateinit var remoteRepo: RemoteDataService

    @MockK
    lateinit var localRepo: LocalDataService

    @Before
    fun beforeEachTest() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `Mapper should map the ExchangeRate and supported db DTOs to the domainMdle and return it`() {

        doMocking()


        val useCase = FetchCurrncyRtsUseCase(localRepo, remoteRepo)


        val testObsver = useCase.execute("", 1.0).test()
        testObsver.hasSubscription()
        testObsver.assertComplete()
        testObsver.assertValueCount(2)
//        testObsver.assertValueAt(0){it.produce(AgreegatedDomainMdl()).isLoading}
        val result = testObsver.values()
         val expectedLoading = result[0].produce(AgreegatedDomainMdl()).isLoading
        val expectedDataLoaded = result[1].produce(AgreegatedDomainMdl()) as AgreegatedDomainMdl
        Truth.assertWithMessage("Initial state is not loading")
            .that(expectedLoading)
            .isTrue()
        Truth.assertWithMessage("Response is not successful")
            .that(expectedDataLoaded.isSuccess)
            .isTrue()

        Truth.assertWithMessage("incorrect currencies data")
            .that(expectedDataLoaded.supportedCurrencies.size)
            .isEqualTo(getCurrncyNetworkDto().supportedCurrencies.size)

        Truth.assertWithMessage("incorrect exchange data")
            .that((expectedDataLoaded.exchangeRates.size))
            .isEqualTo(getExchngeRateNetworkDto().exchangeRates.size)

    }


    private fun doMocking() {

        every { remoteRepo.getCurencies() } returns Single.just(getCurrncyNetworkDto())
        every { remoteRepo.getExchageRates() } returns Single.just(getExchngeRateNetworkDto())

        every { localRepo.saveCurrencies(any()) } returns Completable.complete()
        every { localRepo.saveExchangeRates(any()) } returns Completable.complete()

        every { localRepo.getCurrencies() } returns Maybe.fromCompletable { it.onComplete() }
        every { localRepo.getExchangeRates() } returns Maybe.fromCompletable { it.onComplete() }

    }


}

