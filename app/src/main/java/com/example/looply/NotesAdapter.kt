package com.example.looply

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.looply.R
import android.os.Bundle


class NotesAdapter(
    private var notes: List<Note>,
    private val context: Context
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.project_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.dateTextView.text = note.date
        holder.noteTextView.text = note.note

// Ganti kode di dalam onBindViewHolder
        holder.updateButton.setOnClickListener {
            // Pastikan Anda menggunakan FragmentTransaction untuk mengganti fragment
            val fragment = UpdateProjectFragment()
            val bundle = Bundle()
            bundle.putSerializable("note", note)  // Mengirim data Note ke fragment
            fragment.arguments = bundle

            // Ganti fragment di dalam fragment container (pastikan R.id.fragment_container ada di layout)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)  // Opsional, jika Anda ingin fragment ini masuk ke back stack
                .commit()
        }


        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Project Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = notes.size

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
