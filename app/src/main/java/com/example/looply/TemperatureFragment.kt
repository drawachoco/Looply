package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentTemperatureBinding

class TemperatureFragment : Fragment() {

    private var _binding: FragmentTemperatureBinding? = null
    private val binding get() = _binding!!

    private val temperatureUnits = arrayOf("Celsius", "Fahrenheit", "Kelvin")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using view binding
        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        val view = binding.root

        // Set up Spinner adapters
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, temperatureUnits)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spFromUnit.adapter = adapter
        binding.spToUnit.adapter = adapter

        // Handle conversion on button click
        binding.btnConvert.setOnClickListener {
            val inputTemperature = binding.etTemperatureInput.text.toString().toDoubleOrNull()
            if (inputTemperature != null) {
                val fromUnit = binding.spFromUnit.selectedItem.toString()
                val toUnit = binding.spToUnit.selectedItem.toString()

                val convertedTemperature = convertTemperature(inputTemperature, fromUnit, toUnit)

                // Display results
                binding.tvTemperatureInput.text = "Input Temperature: $inputTemperature $fromUnit"
                binding.tvConvertedTemperature.text = "Converted Temperature: $convertedTemperature $toUnit"
                binding.tvLastUpdated.text = "Last Update: ${System.currentTimeMillis()}"

                binding.linearResult.visibility = View.VISIBLE
            }
        }

        return view
    }

    // Temperature conversion logic
    private fun convertTemperature(input: Double, fromUnit: String, toUnit: String): Double {
        var tempInCelsius = 0.0

        // Convert input to Celsius first
        when (fromUnit) {
            "Celsius" -> tempInCelsius = input
            "Fahrenheit" -> tempInCelsius = (input - 32) * 5 / 9
            "Kelvin" -> tempInCelsius = input - 273.15
        }

        // Convert Celsius to the desired unit
        return when (toUnit) {
            "Celsius" -> tempInCelsius
            "Fahrenheit" -> (tempInCelsius * 9 / 5) + 32
            "Kelvin" -> tempInCelsius + 273.15
            else -> tempInCelsius
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
