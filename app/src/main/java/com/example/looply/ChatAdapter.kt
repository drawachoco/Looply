package com.example.looply

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.looply.databinding.ItemChatBinding
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val messageList: MutableList<ChatMessage>,
    private val currentUser: String
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = messageList[position]

        // Set isi pesan
        holder.binding.tvMessage.text = chatMessage.message

        // Format timestamp
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        holder.binding.tvTimestamp.text = sdf.format(Date(chatMessage.timestamp))

        // Penyesuaian posisi bubble berdasarkan pengirim
        val layoutParams = holder.binding.layoutMessage.layoutParams as FrameLayout.LayoutParams
        if (chatMessage.sender == currentUser) {
            // Bubble user sendiri (kanan)
            layoutParams.gravity = android.view.Gravity.END
            holder.binding.layoutMessage.layoutParams = layoutParams
            holder.binding.tvMessage.background =
                ContextCompat.getDrawable(holder.binding.root.context, R.drawable.message_bubble_user)
            holder.binding.tvTimestamp.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        } else {
            // Bubble user lain (kiri)
            layoutParams.gravity = android.view.Gravity.START
            holder.binding.layoutMessage.layoutParams = layoutParams
            holder.binding.tvMessage.background =
                ContextCompat.getDrawable(holder.binding.root.context, R.drawable.message_bubble_other)
            holder.binding.tvTimestamp.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        }
    }

    override fun getItemCount(): Int = messageList.size
}
