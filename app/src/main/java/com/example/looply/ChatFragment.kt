package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.looply.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var database: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()
    private var chatId: String = ""
    private var contactName: String? = null

    companion object {
        fun newInstance(chatId: String, contactName: String): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString("chatId", chatId)
            args.putString("contactName", contactName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatId = arguments?.getString("chatId") ?: ""
        contactName = arguments?.getString("contactName")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance("https://looply-v1-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("chats/$chatId/messages")

        // Set nama kontak di Toolbar
        binding.toolbarChat.title = contactName

        chatAdapter = ChatAdapter(messageList, FirebaseAuth.getInstance().currentUser?.email ?: "")
        binding.rvMessages.adapter = chatAdapter
        binding.rvMessages.layoutManager = LinearLayoutManager(context)

        fetchMessages()

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
            } else {
                Toast.makeText(context, "Message cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun fetchMessages() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(ChatMessage::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                chatAdapter.notifyDataSetChanged()
                binding.rvMessages.scrollToPosition(messageList.size - 1) // Scroll otomatis
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load messages: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendMessage(message: String) {
        val messageId = database.push().key
        val sender = FirebaseAuth.getInstance().currentUser?.email ?: "Unknown User"
        val newChatMessage = ChatMessage(sender, message, System.currentTimeMillis())

        if (messageId != null) {
            database.child(messageId).setValue(newChatMessage).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.etMessage.text.clear()
                } else {
                    Toast.makeText(context, "Failed to send message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
