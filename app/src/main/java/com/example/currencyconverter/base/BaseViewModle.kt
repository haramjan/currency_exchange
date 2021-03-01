package com.example.currencyconverter.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.base.BaseMdl
import com.example.currencyconverter.base.Result
import com.example.currencyconverter.domain.modle.AgreegatedDomainMdl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModle : ViewModel() {
    private val data = MutableLiveData<BaseMdl>()
    val dataProvidr: LiveData<BaseMdl>
        get() = data


    private val disposableStore = CompositeDisposable()

    protected fun postData(obsrble: Observable<Result>, initialState: BaseMdl) {
        val disposable = obsrble
            .scan(initialState, this::resultProducer)
            .subscribe {
                data.postValue(it)
            }
        disposableStore.add(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        disposableStore.dispose()
        disposableStore.clear()

    }


    private fun resultProducer(
        lastState: BaseMdl,
        result: Result
    ) = result.produce(lastState)
}