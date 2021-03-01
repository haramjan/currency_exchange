package com.example.currencyconverter.base

import com.example.currencyconverter.common.ResponseCodes

abstract class Result {
    abstract fun produce(state: BaseMdl): BaseMdl

    object Loading : Result() {
        override fun produce(state: BaseMdl): BaseMdl {
            return state.apply { isLoading = true }
        }
    }

    data class Success(val cod: Int = ResponseCodes.OK) : Result() {
        override fun produce(state: BaseMdl): BaseMdl {
            return state.apply {
                isLoading = false
                isSuccess = true
                responseCode = cod
            }
        }
    }

    data class Error(val message: String = "", val cod: Int = ResponseCodes.DEFAULT, val isShowError:Boolean = false) : Result() {
        override fun produce(state: BaseMdl): BaseMdl {
            return state.apply {
                isLoading = false
                isSuccess = false
                responseCode = cod
                errorMessage = message
            }
        }
    }
}


