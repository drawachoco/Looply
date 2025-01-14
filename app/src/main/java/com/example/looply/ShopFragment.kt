package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentShopBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout using ViewBinding
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.new1.setOnClickListener {
            val fragment = SleepypolarFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.new2.setOnClickListener {
            val fragment = AnimalGhostFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.new3.setOnClickListener {
            val fragment = MinioctopusFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.mr.setOnClickListener {
            val fragment = MagicringFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.hold.setOnClickListener {
            val fragment = HoldhookyarnFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.others.setOnClickListener {
            val fragment = TutorialFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }


}
