package com.example.looply

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.looply.databinding.FragmentNewprojectBinding

class NewprojectFragment : Fragment() {
    private var _binding: FragmentNewprojectBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: NotesDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        _binding = FragmentNewprojectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize database helper
        db = NotesDatabaseHelper(requireContext())

        // Set click listener for save button
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val date = binding.dateEditText.text.toString()
            val note = binding.noteEditText.text.toString()

            if (title.isEmpty() || date.isEmpty() || note.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new Note instance
            val notee = Note(0, title, date, note)
            Log.d("NewProjectFragment", "Inserting note: $note")
            db.insertNote(notee)

            // Show success message and navigate back
            Toast.makeText(requireContext(), "Project Saved", Toast.LENGTH_SHORT).show()

            // Explicitly replace with MyprojectFragment instead of popBackStack
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
