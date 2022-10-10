package com.example.notewithroomdatabase.adapter

import com.example.notewithroomdatabase.model.Note

interface ClickListener {
    fun onClickListener(note: Note,pos:Int)
}