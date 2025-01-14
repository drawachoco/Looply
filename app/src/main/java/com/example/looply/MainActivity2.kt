package com.example.looply

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.looply.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomnavview.selectedItemId = R.id.bottom_home

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Set the listener for the bottom navigation view
        binding.bottomnavview.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_myproject -> replaceFragment(MyprojectFragment())
                R.id.bottom_shop -> replaceFragment(ShopFragment())
                R.id.bottom_message -> replaceFragment(MessageFragment())
                R.id.bottom_profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    // Function to replace the current fragment with a new one
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
