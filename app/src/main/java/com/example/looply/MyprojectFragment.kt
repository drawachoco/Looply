package com.example.looply

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.looply.databinding.FragmentMyprojectBinding

class MyprojectFragment : Fragment() {

    private var _binding: FragmentMyprojectBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for the fragment using ViewBinding
        _binding = FragmentMyprojectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database helper
        db = NotesDatabaseHelper(requireContext())

        // Initialize the adapter with notes from the database
        notesAdapter = NotesAdapter(db.getAllNotes(), requireContext())

        // Set up RecyclerView
        binding.projectRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.projectRecyclerView.adapter = notesAdapter

        // Set up the click listener for the add button
        binding.addButton.setOnClickListener {
            // Replace the current fragment with NewprojectFragment
            val newProjectFragment = NewprojectFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newProjectFragment)
                .addToBackStack(null)  // Optional, to add fragment to back stack
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the adapter with updated data when the fragment resumes
        notesAdapter.refreshData(db.getAllNotes())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Nullify binding to prevent memory leaks
    }
}
