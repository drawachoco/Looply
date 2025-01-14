package com.example.looply

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi FirebaseAuth dan Database
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://looply-v1-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val email = currentUser.email ?: "No Email"
            binding.tvEmail.text = email

            // Ambil nama berdasarkan email dari Firebase Realtime Database
            database.addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    var userName = "Unknown User"
                    for (userSnapshot in snapshot.children) {
                        val userEmail = userSnapshot.child("email").value as? String
                        if (userEmail == email) {
                            userName = userSnapshot.child("name").value as? String ?: "Unknown User"
                            break
                        }
                    }
                    binding.tvName.text = userName
                }

                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                    Toast.makeText(context, "Failed to load profile: ${error.message}", Toast.LENGTH_SHORT).show()
                    binding.tvName.text = "Unknown User"
                }
            })
        } else {
            binding.tvName.text = "Unknown User"
            binding.tvEmail.text = "No Email"
        }

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

            // Redirect ke LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hindari memory leaks
    }
}
