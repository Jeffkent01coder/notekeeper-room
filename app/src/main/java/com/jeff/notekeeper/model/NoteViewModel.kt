package com.jeff.notekeeper.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeff.notekeeper.room.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel class for the NoteKeeper app.
 * It manages UI-related data and communicates with the Repository.
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // Expose the list of notes as a StateFlow to the UI.
    val notes: StateFlow<List<Note>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    /**
     * Insert a new note using the Repository.
     * This operation runs on a background thread via coroutines.
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    /**
     * Delete an existing note using the Repository.
     * This operation runs on a background thread via coroutines.
     */
    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}