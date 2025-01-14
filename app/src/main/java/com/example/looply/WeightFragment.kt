package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class WeightFragment : Fragment() {

    private lateinit var spWeightFrom: Spinner
    private lateinit var spWeightTo: Spinner
    private lateinit var etInputWeight: EditText
    private lateinit var btnConvertWeight: Button
    private lateinit var tvTitleResult: TextView
    private lateinit var tvWeightFrom: TextView
    private lateinit var tvWeightTo: TextView
    private lateinit var tvDateNow: TextView
    private lateinit var linearResult: View

    private val weightUnits = arrayOf("Kilogram", "Gram", "Milligram", "Pound", "Ounce", "Stone", "Ton", "Carat")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_weight, container, false)

        spWeightFrom = binding.findViewById(R.id.spWeightFrom)
        spWeightTo = binding.findViewById(R.id.spWeightTo)
        etInputWeight = binding.findViewById(R.id.etInputWeight)
        btnConvertWeight = binding.findViewById(R.id.btnConvertWeight)
        tvTitleResult = binding.findViewById(R.id.tvTitleResult)
        tvWeightFrom = binding.findViewById(R.id.tvWeightFrom)
        tvWeightTo = binding.findViewById(R.id.tvWeightTo)
        tvDateNow = binding.findViewById(R.id.tvDateNow)
        linearResult = binding.findViewById(R.id.linearResult)

        // Set up Spinners
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, weightUnits)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spWeightFrom.adapter = adapter
        spWeightTo.adapter = adapter

        btnConvertWeight.setOnClickListener {
            convertWeight()
        }

        return binding
    }

    private fun convertWeight() {
        val fromUnit = spWeightFrom.selectedItem.toString()
        val toUnit = spWeightTo.selectedItem.toString()
        val inputWeight = etInputWeight.text.toString().toDoubleOrNull()

        if (inputWeight != null) {
            // Perform conversion based on selected units
            val result = when {
                fromUnit == "Kilogram" && toUnit == "Gram" -> inputWeight * 1000
                fromUnit == "Kilogram" && toUnit == "Milligram" -> inputWeight * 1_000_000
                fromUnit == "Kilogram" && toUnit == "Pound" -> inputWeight * 2.20462
                fromUnit == "Kilogram" && toUnit == "Ounce" -> inputWeight * 35.274
                fromUnit == "Kilogram" && toUnit == "Stone" -> inputWeight * 0.157473
                fromUnit == "Kilogram" && toUnit == "Ton" -> inputWeight / 1000
                fromUnit == "Kilogram" && toUnit == "Carat" -> inputWeight * 5000
                fromUnit == "Gram" && toUnit == "Kilogram" -> inputWeight / 1000
                fromUnit == "Gram" && toUnit == "Milligram" -> inputWeight * 1000
                fromUnit == "Gram" && toUnit == "Pound" -> inputWeight / 453.592
                fromUnit == "Gram" && toUnit == "Ounce" -> inputWeight / 28.3495
                fromUnit == "Gram" && toUnit == "Stone" -> inputWeight / 6350.293
                fromUnit == "Gram" && toUnit == "Ton" -> inputWeight / 1_000_000
                fromUnit == "Gram" && toUnit == "Carat" -> inputWeight * 5
                fromUnit == "Milligram" && toUnit == "Kilogram" -> inputWeight / 1_000_000
                fromUnit == "Milligram" && toUnit == "Gram" -> inputWeight / 1000
                fromUnit == "Milligram" && toUnit == "Pound" -> inputWeight / 453_592.37
                fromUnit == "Milligram" && toUnit == "Ounce" -> inputWeight / 28_349.5
                fromUnit == "Milligram" && toUnit == "Stone" -> inputWeight / 6_350_293
                fromUnit == "Milligram" && toUnit == "Ton" -> inputWeight / 1_000_000_000
                fromUnit == "Milligram" && toUnit == "Carat" -> inputWeight / 200
                fromUnit == "Pound" && toUnit == "Kilogram" -> inputWeight / 2.20462
                fromUnit == "Pound" && toUnit == "Gram" -> inputWeight * 453.592
                fromUnit == "Pound" && toUnit == "Milligram" -> inputWeight * 453_592.37
                fromUnit == "Pound" && toUnit == "Ounce" -> inputWeight * 16
                fromUnit == "Pound" && toUnit == "Stone" -> inputWeight / 14
                fromUnit == "Pound" && toUnit == "Ton" -> inputWeight / 2_204.62
                fromUnit == "Pound" && toUnit == "Carat" -> inputWeight * 2267.96
                fromUnit == "Ounce" && toUnit == "Kilogram" -> inputWeight / 35.274
                fromUnit == "Ounce" && toUnit == "Gram" -> inputWeight * 28.3495
                fromUnit == "Ounce" && toUnit == "Milligram" -> inputWeight * 28_349.5
                fromUnit == "Ounce" && toUnit == "Pound" -> inputWeight / 16
                fromUnit == "Ounce" && toUnit == "Stone" -> inputWeight / 224
                fromUnit == "Ounce" && toUnit == "Ton" -> inputWeight / 35_274
                fromUnit == "Ounce" && toUnit == "Carat" -> inputWeight * 141.7476
                fromUnit == "Stone" && toUnit == "Kilogram" -> inputWeight / 0.157473
                fromUnit == "Stone" && toUnit == "Gram" -> inputWeight * 6350.293
                fromUnit == "Stone" && toUnit == "Milligram" -> inputWeight * 6_350_293
                fromUnit == "Stone" && toUnit == "Pound" -> inputWeight * 14
                fromUnit == "Stone" && toUnit == "Ounce" -> inputWeight * 224
                fromUnit == "Stone" && toUnit == "Ton" -> inputWeight / 157.473
                fromUnit == "Stone" && toUnit == "Carat" -> inputWeight * 1_000_000
                fromUnit == "Ton" && toUnit == "Kilogram" -> inputWeight * 1000
                fromUnit == "Ton" && toUnit == "Gram" -> inputWeight * 1_000_000
                fromUnit == "Ton" && toUnit == "Milligram" -> inputWeight * 1_000_000_000
                fromUnit == "Ton" && toUnit == "Pound" -> inputWeight * 2204.62
                fromUnit == "Ton" && toUnit == "Ounce" -> inputWeight * 35_274
                fromUnit == "Ton" && toUnit == "Stone" -> inputWeight * 157.473
                fromUnit == "Ton" && toUnit == "Carat" -> inputWeight * 5_000_000
                fromUnit == "Carat" && toUnit == "Kilogram" -> inputWeight / 5000
                fromUnit == "Carat" && toUnit == "Gram" -> inputWeight / 5
                fromUnit == "Carat" && toUnit == "Milligram" -> inputWeight * 200
                fromUnit == "Carat" && toUnit == "Pound" -> inputWeight / 2267.96
                fromUnit == "Carat" && toUnit == "Ounce" -> inputWeight / 141.7476
                fromUnit == "Carat" && toUnit == "Stone" -> inputWeight / 1_000_000
                fromUnit == "Carat" && toUnit == "Ton" -> inputWeight / 5_000_000
                else -> 0.0
            }

            // Update UI with the result
            tvWeightFrom.text = "Input Weight: $inputWeight $fromUnit"
            tvWeightTo.text = "Converted Weight: $result $toUnit"

            // Show result view
            linearResult.visibility = View.VISIBLE

            // Update date/time (assuming the conversion was just performed)
            tvDateNow.text = "Last Update: ${System.currentTimeMillis()}"
        } else {
            // Show error if input is invalid
            tvWeightFrom.text = "Invalid input. Please enter a valid number."
            tvWeightTo.text = ""
            linearResult.visibility = View.GONE
        }
    }
}
