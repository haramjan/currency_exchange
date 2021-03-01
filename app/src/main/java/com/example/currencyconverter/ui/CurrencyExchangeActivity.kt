package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.currencyconverter.R
import com.example.currencyconverter.base.BaseActivity
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyExchangeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_exchange)
        supportActionBar?.title = "Exchange Rates"
        init()
    }

    private fun init(){
        val fragment = CurrencyExchangeFragment()
        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.fragmentContainer,fragment).commitNow()
    }
}