package com.jeff.notekeeper.room

import com.jeff.notekeeper.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository class abstracts access to the data source.
 * In this case, it manages data operations for notes.
 */
class NoteRepository(private val noteDao: NoteDao) {

    // Expose all notes as a Flow to observe database changes.
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    /**
     * Insert a new note into the database.
     * The withContext(Dispatchers.IO) ensures this operation runs on a background thread.
     */
    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    /**
     * Delete an existing note from the database.
     * The withContext(Dispatchers.IO) ensures this operation runs on a background thread.
     */
    suspend fun delete(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }
}
