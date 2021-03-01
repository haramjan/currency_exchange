package com.example.currencyconverter.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun renderView(statMdl: BaseMdl)
    abstract fun getViewModle(): BaseViewModle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
             getViewModle().dataProvidr
            .observe(viewLifecycleOwner, { renderView(it) })
    }

    protected fun showToast(message: String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }
}