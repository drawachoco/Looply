package com.example.looply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.looply.databinding.ItemContactBinding

class ContactAdapter(
    private val contacts: List<Contact>,
    private val onClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.tvContactName.text = contact.name
        holder.binding.tvContactEmail.text = contact.email

        // Set click listener
        holder.itemView.setOnClickListener {
            onClick(contact)
        }
    }

    override fun getItemCount(): Int = contacts.size
}
