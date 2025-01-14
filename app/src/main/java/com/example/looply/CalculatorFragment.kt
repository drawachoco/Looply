package com.example.looply

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentCalculatorBinding
import com.example.looply.R

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private var currentInput: String = ""
    private var operation: String? = null
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize View Binding
        _binding = FragmentCalculatorBinding.bind(view)

        // Setup click listeners
        binding.apply {
            // Number buttons
            button0.setOnClickListener { numberAction(it) }
            button1.setOnClickListener { numberAction(it) }
            button2.setOnClickListener { numberAction(it) }
            button3.setOnClickListener { numberAction(it) }
            button4.setOnClickListener { numberAction(it) }
            button5.setOnClickListener { numberAction(it) }
            button6.setOnClickListener { numberAction(it) }
            button7.setOnClickListener { numberAction(it) }
            button8.setOnClickListener { numberAction(it) }
            button9.setOnClickListener { numberAction(it) }
            buttonDot.setOnClickListener { numberAction(it) }

            // Operator buttons
            buttonAdd.setOnClickListener { operationAction(it) }
            buttonSub.setOnClickListener { operationAction(it) }
            buttonMul.setOnClickListener { operationAction(it) }
            buttonDiv.setOnClickListener { operationAction(it) }

            // Other actions
            buttonAC.setOnClickListener { allClearAction() }
            buttonBackspace.setOnClickListener { backSpaceAction() }
            buttonEquals.setOnClickListener { equalsAction() }
        }
    }

    // Action when a number button is clicked
    private fun numberAction(view: View) {
        val button = view as Button
        currentInput += button.text
        binding.workingsTV.text = currentInput
    }

    // Action when an operator button is clicked
    private fun operationAction(view: View) {
        val button = view as Button
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toDouble()
            operation = button.text.toString()
            currentInput = ""
            binding.workingsTV.text = ""
        }
    }

    // Action to clear the current input
    private fun allClearAction() {
        currentInput = ""
        operand1 = 0.0
        operand2 = 0.0
        operation = null
        binding.workingsTV.text = ""
        binding.resultsTV.text = ""
    }

    // Action to delete the last character
    private fun backSpaceAction() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            binding.workingsTV.text = currentInput
        }
    }

    // Action when the equals button is clicked to perform calculation
    private fun equalsAction() {
        if (currentInput.isNotEmpty() && operation != null) {
            operand2 = currentInput.toDouble()

            val result = when (operation) {
                "/" -> operand1 / operand2
                "x" -> operand1 * operand2
                "-" -> operand1 - operand2
                "+" -> operand1 + operand2
                else -> null
            }

            if (result != null) {
                binding.resultsTV.text = result.toString()
                currentInput = result.toString()
                operand1 = result
            } else {
                Toast.makeText(requireContext(), "Invalid operation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Clean up the binding reference when the fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
