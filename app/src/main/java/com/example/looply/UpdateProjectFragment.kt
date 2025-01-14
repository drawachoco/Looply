package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentUpdateProjectBinding

class UpdateProjectFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProjectBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1  // Default to -1 if no ID is passed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve note ID from the arguments passed by the previous fragment or activity
        arguments?.let {
            noteId = it.getInt("note_id", -1)
        }

        if (noteId == -1) {
            // If no valid note ID is passed, return or handle error (perhaps navigate back)
            activity?.onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProjectBinding.inflate(inflater, container, false)

        // Initialize the database helper
        db = NotesDatabaseHelper(requireContext())  // Use requireContext() to get the context

        // Fetch the note details using noteId
        val note = db.getNoteByID(noteId)
        if (note != null) {
            // Populate the input fields with the note details
            binding.updateTitleEditText.setText(note.title)
            binding.updateDateEditText.setText(note.date)
            binding.updateNoteEditText.setText(note.note)
        }

        // Handle the save button click to update the note
        binding.updateButton.setOnClickListener {
            val updatedTitle = binding.updateTitleEditText.text.toString()
            val updatedDate = binding.updateDateEditText.text.toString()
            val updatedNote = binding.updateNoteEditText.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDate.isNotEmpty() && updatedNote.isNotEmpty()) {
                // Create an updated Note object with the new data
                val updatedNoteObj = Note(noteId, updatedTitle, updatedDate, updatedNote)

                // Update the note in the database
                val rowsUpdated = db.updateNote(updatedNoteObj)

                if (rowsUpdated > 0) {
                    Toast.makeText(requireContext(), "Changes saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to save changes", Toast.LENGTH_SHORT).show()
                }

                // Close the fragment and return to the previous screen
                activity?.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
