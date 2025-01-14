package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentCurrencyBinding
import java.text.SimpleDateFormat
import java.util.*

class CurrencyFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyBinding

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "IDR" to 15000.0,
        "GBP" to 0.75,
        "JPY" to 110.0,
        "AUD" to 1.4,
        "CAD" to 1.25,
        "CHF" to 0.92,
        "CNY" to 6.45,
        "INR" to 74.0
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the binding layout
        binding = FragmentCurrencyBinding.inflate(inflater, container, false)

        // Set up spinners
        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spCountry1.adapter = adapter
        binding.spCountry2.adapter = adapter

        // Handle button click
        binding.btnHitung.setOnClickListener {
            calculateConversion()
        }

        return binding.root
    }

    private fun calculateConversion() {
        val currencyFrom = binding.spCountry1.selectedItem.toString()
        val currencyTo = binding.spCountry2.selectedItem.toString()

        val inputNominal = binding.etInputNominal.text.toString()
        if (inputNominal.isEmpty()) {
            Toast.makeText(requireContext(), "Insert Nominal", Toast.LENGTH_SHORT).show()
            return
        }

        val nominal = inputNominal.toDouble()
        val rateFrom = exchangeRates[currencyFrom] ?: 1.0
        val rateTo = exchangeRates[currencyTo] ?: 1.0

        val convertedValue = nominal * (rateTo / rateFrom)

        // Display results
        binding.tvCurrencyFrom.text = "Nominal Input: $nominal $currencyFrom"
        binding.tvCurrencyTo.text = "Result: %.2f $currencyTo".format(convertedValue)

        val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        binding.tvDateNow.text = "Last Update: $currentDate"

        binding.linearHasil.visibility = View.VISIBLE
    }
}
