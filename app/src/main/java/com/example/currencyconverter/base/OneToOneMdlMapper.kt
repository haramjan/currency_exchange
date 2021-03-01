package com.example.currencyconverter.base

interface OneToOneMdlMapper<in I, out O> {
    fun map(type: I): O
}