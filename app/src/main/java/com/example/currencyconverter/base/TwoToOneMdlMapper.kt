package com.example.currencyconverter.base

interface TwoToOneMdlMapper<in I1,in I2, out O> {
    fun map(type1: I1,type2: I2): O
}