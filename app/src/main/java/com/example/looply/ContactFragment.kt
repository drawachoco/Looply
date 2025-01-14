package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.looply.databinding.FragmentContactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private lateinit var database: DatabaseReference
    private lateinit var contactAdapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance("https://looply-v1-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

        // Set up RecyclerView with ContactAdapter
        contactAdapter = ContactAdapter(contactList) { contact ->
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""
            val chatId = generateChatId(currentUserEmail, contact.email)

            // Navigate to ChatFragment and pass the contact's name
            val fragment = ChatFragment.newInstance(chatId, contact.name)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvContacts.layoutManager = LinearLayoutManager(context)
        binding.rvContacts.adapter = contactAdapter

        // Fetch contacts from Firebase
        fetchContacts()

        return binding.root
    }

    private fun fetchContacts() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                for (userSnapshot in snapshot.children) {
                    val contact = userSnapshot.getValue(Contact::class.java)
                    if (contact != null && contact.email != FirebaseAuth.getInstance().currentUser?.email) {
                        contactList.add(contact)
                    }
                }
                contactAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load contacts: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun generateChatId(user1: String, user2: String): String {
        val sanitizedUser1 = user1.replace(".", "_")
        val sanitizedUser2 = user2.replace(".", "_")
        return listOf(sanitizedUser1, sanitizedUser2).sorted().joinToString("_")
    }
}
