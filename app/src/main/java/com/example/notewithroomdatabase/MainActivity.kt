package com.example.notewithroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notewithroomdatabase.adapter.ClickListener
import com.example.notewithroomdatabase.adapter.NoteAdapter
import com.example.notewithroomdatabase.database.NoteDatabase
import com.example.notewithroomdatabase.databinding.ActivityMainBinding
import com.example.notewithroomdatabase.databinding.CreateAlertDialogBinding
import com.example.notewithroomdatabase.databinding.EditAlertDialogBinding
import com.example.notewithroomdatabase.model.Note

class MainActivity : AppCompatActivity(), ClickListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val noteDatabase by lazy { NoteDatabase.getInstance(this) }
    private lateinit var noteAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        noteAdapter = NoteAdapter(this)
        setupRv()

        binding.fab.setOnClickListener {
            val binding = CreateAlertDialogBinding.inflate(LayoutInflater.from(this@MainActivity))
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setView(binding.root)
            binding.btnSave.setOnClickListener {
                val title = binding.editTitle.text.toString().trim()
                val description = binding.editDescription.text.toString().trim()
                if (title.isNotBlank() && description.isNotBlank() && title.isNotEmpty() && description.isNotEmpty()) {
                    noteDatabase.dao().saveNote(Note(0, title, description))
                    Toast.makeText(this@MainActivity, "Note saved!", Toast.LENGTH_SHORT).show()
                    setupRv()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "Enter data!", Toast.LENGTH_SHORT).show()
                }
                setupRv()

            }
            setupRv()
            binding.btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun setupRv() = binding.rv.apply {
        layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        adapter = noteAdapter
        noteAdapter.noteList = noteDatabase.dao().getAllNotes()
    }
    private fun editNote(note: Note,pos: Int){
        val bnE = EditAlertDialogBinding.inflate(LayoutInflater.from(this))
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(bnE.root)
        bnE.apply {
            editedTitle.setText(note.title)
            editedDescription.setText(note.description)

            btnEdit.setOnClickListener {
                val title = bnE.editedTitle.text.toString().trim()
                val description = bnE.editedDescription.text.toString().trim()
                if (title.isNotBlank() && description.isNotBlank() && title.isNotEmpty() && description.isNotEmpty()) {
                    noteDatabase.dao().editNote(Note(note.id, title, description))
                    Toast.makeText(this@MainActivity, "Note edited!", Toast.LENGTH_SHORT).show()
                    setupRv()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "Enter data!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        alertDialog.show()
    }

    override fun onClickListener(note: Note,pos:Int) {
        editNote(note,pos)
    }


}
