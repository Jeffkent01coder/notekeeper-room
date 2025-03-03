package com.jeff.notekeeper.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeff.notekeeper.room.NoteRepository

/**
 * Factory class to create instances of NoteViewModel with required parameters.
 */
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}