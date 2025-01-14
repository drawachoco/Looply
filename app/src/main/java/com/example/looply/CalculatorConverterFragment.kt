package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentCalculatorConverterBinding

class CalculatorConverterFragment : Fragment() {

    private var _binding: FragmentCalculatorConverterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for the fragment using ViewBinding
        _binding = FragmentCalculatorConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup dropdown menu using binding
        val options = arrayOf("Calculator", "Currency Converter", "Temperature Converter", "Weight Converter")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        binding.dropdownMenu.adapter = adapter

        // Set default fragment
        replaceFragment(CalculatorFragment())

        // Handle dropdown menu selection using binding
        binding.dropdownMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> replaceFragment(CalculatorFragment()) // Calculator
                    1 -> replaceFragment(CurrencyFragment()) // Currency Converter
                    2 -> replaceFragment(TemperatureFragment()) // Temperature Converter
                    3 -> replaceFragment(WeightFragment()) // Weight Converter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Nullify binding to prevent memory leaks
    }
}
