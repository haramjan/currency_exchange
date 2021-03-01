package com.example.currencyconverter.base

import com.example.currencyconverter.common.ResponseCodes

abstract class BaseMdl {
    var isLoading: Boolean = false
    var isSuccess: Boolean = false
    var isShowError: Boolean = false
    var errorMessage: String = ""
    var responseCode: Int = ResponseCodes.DEFAULT
}



