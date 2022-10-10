package com.example.notewithroomdatabase.database

import androidx.room.*
import com.example.notewithroomdatabase.model.Note

@Dao //Database access object
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNote(note: Note)

    @Update(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
    fun editNote(note: Note)

    @Query("SELECT * FROM NoteTable")
    fun getAllNotes():List<Note>
}