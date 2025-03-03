package com.jeff.notekeeper.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeff.notekeeper.model.Note

/**
 * Room Database class for the NoteKeeper app.
 * It serves as the main access point for the app's persisted data.
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    // Abstract method to get the DAO for Note operations.
    abstract fun noteDao(): NoteDao

    companion object {
        // Volatile variable to ensure the instance is visible across threads.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        /**
         * Returns the singleton instance of the NoteDatabase.
         * If the database instance doesn't exist, it is created.
         */
        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                // Build the Room database instance.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}