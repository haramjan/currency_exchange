package com.example.currencyconverter.base

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.RoundingMode
import java.text.DecimalFormat


abstract class BaseUseCase {


    protected fun <T> doProcessing(observable: Observable<T>): Observable<Result> {
        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .cast(Result::class.java)
            .onErrorReturn {
                Result.Error("Error, please try again", 100)
            }
            .startWith(Result.Loading)

    }

    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}

