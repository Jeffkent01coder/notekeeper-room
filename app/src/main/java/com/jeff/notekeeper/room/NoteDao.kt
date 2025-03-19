package com.jeff.notekeeper.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import com.jeff.notekeeper.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for performing database operations on the Note entity.
 * The Flow return type allows for reactive data updates.
 */
@Dao
interface NoteDao {

    // Query to get all notes ordered by id in descending order.
    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    // Insert a new note into the database.
    @Insert
   fun insertNote(note: Note)

    // Delete an existing note from the database.
    @Delete
    fun deleteNote(note: Note)
}