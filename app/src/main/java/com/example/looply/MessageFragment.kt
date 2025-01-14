package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate and bind the view using DataBindingUtil
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load ContactFragment as the default fragment when MessageFragment is opened
        if (savedInstanceState == null) {
            goToFragment(ContactFragment())
        }

        // Set up button click listeners
        binding.buttonFragment1.setOnClickListener {
            goToFragment(ContactFragment())
        }

        binding.buttonFragment2.setOnClickListener {
            goToFragment(ChatFragment())
        }
    }

    private fun goToFragment(fragment: Fragment) {
        // Use parentFragmentManager for navigating between fragments
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer, fragment)
            .commit()
    }



}
