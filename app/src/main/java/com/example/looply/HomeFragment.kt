package com.example.looply

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up click listeners using binding
        binding.menuButton.setOnClickListener {
            showBottomMenu()
        }

        binding.toshop.setOnClickListener {
            val fragment = ShopFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.calculator.setOnClickListener {
            val fragment = CalculatorConverterFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.messagebutton.setOnClickListener {
            val fragment = MessageFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.buttonaddproject.setOnClickListener {
            val fragment = NewprojectFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.newproject.setOnClickListener {
            val fragment = NewprojectFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.freepattern1.setOnClickListener {
            val fragment = TutorialNofaceFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.freepattern2.setOnClickListener {
            val fragment = TutorialSootspritesFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.freepattern3.setOnClickListener {
            val fragment = TutorialCapysnoopyFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showBottomMenu() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheet_layout)

        // Access dialog elements using findViewById inside the dialog layout
        val newProject2: ImageView = dialog.findViewById(R.id.newproject2)
        val calculator2: ImageView = dialog.findViewById(R.id.calculator2)
        val message2: ImageView = dialog.findViewById(R.id.message2)
        val settings: ImageView = dialog.findViewById(R.id.settings)
        val tutorial: ImageView = dialog.findViewById(R.id.tutorial)
        val cancelButton: ImageView = dialog.findViewById(R.id.cancelbutton)

        // Set click listeners for dialog buttons
        newProject2.setOnClickListener {
            dialog.dismiss()
            val fragment = NewprojectFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        calculator2.setOnClickListener {
            dialog.dismiss()
            val fragment = CalculatorConverterFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        message2.setOnClickListener {
            dialog.dismiss()
            val fragment = MessageFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        settings.setOnClickListener {
            dialog.dismiss()
            val fragment = SettingsFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        tutorial.setOnClickListener {
            dialog.dismiss()
            val fragment = TutorialFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Configure dialog appearance
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.SlideActivityAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
