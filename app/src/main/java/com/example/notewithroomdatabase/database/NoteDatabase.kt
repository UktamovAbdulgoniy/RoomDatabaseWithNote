package com.example.notewithroomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notewithroomdatabase.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun dao(): NoteDao

    companion object {
        private var instance:NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context):NoteDatabase{
            if (instance == null){
                instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "Note.Db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}