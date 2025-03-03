package com.jeff.notekeeper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jeff.notekeeper.databinding.ActivityMainBinding
import com.jeff.notekeeper.model.Note
import com.jeff.notekeeper.model.NoteViewModel
import com.jeff.notekeeper.model.NoteViewModelFactory
import com.jeff.notekeeper.room.NoteDatabase
import com.jeff.notekeeper.room.NoteRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * MainActivity for the NoteKeeper app.
 * Demonstrates how to use Room, ViewModel, Coroutines, and View Binding together.
 */
class MainActivity : AppCompatActivity() {

    // View Binding instance for activity_main.xml.
    private lateinit var binding: ActivityMainBinding

    // Lazy initialization of the ViewModel using a factory that provides the repository.
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            NoteRepository(
                // Get the DAO from the singleton Room database instance.
                NoteDatabase.getDatabase(applicationContext).noteDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the "Add Note" button.
        binding.btnAddNote.setOnClickListener {
            // Retrieve title and content from the EditText fields.
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            // Create a new Note object.
            val note = Note(title = title, content = content)

            // Insert the note into the database using the ViewModel.
            noteViewModel.insert(note)

            // Clear the input fields after insertion.
            binding.etTitle.text.clear()
            binding.etContent.text.clear()
        }

        // Set click listener for the "Delete Note" button.
        binding.btnDeleteNote.setOnClickListener {
            // Get the current list of notes from the StateFlow.
            val currentNotes = noteViewModel.notes.value
            if (currentNotes.isNotEmpty()) {
                // For demonstration, delete the first note in the list.
                val noteToDelete = currentNotes[0]
                noteViewModel.delete(noteToDelete)
                Toast.makeText(this, "Deleted note: ${noteToDelete.title}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No notes available to delete.", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe changes in the notes list using a coroutine and Flow.
        lifecycleScope.launch {
            noteViewModel.notes.collectLatest { notes ->
                // Build a simple string to display each note.
                val notesText = notes.joinToString(separator = "\n\n") { note ->
                    "ID: ${note.id}\nTitle: ${note.title}\nContent: ${note.content}"
                }
                // Update the TextView to display the current notes.
                binding.tvNotes.text = notesText
            }
        }
    }
}
