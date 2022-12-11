package com.leusoft.taskcore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.leusoft.taskcore.R
import com.leusoft.taskcore.adapters.NoteAdapter
import com.leusoft.taskcore.data.Note
import com.leusoft.taskcore.viewmodel.NoteViewModel

class EditNoteActivity : AppCompatActivity() {

    private lateinit var mNoteViewModel: NoteViewModel

    lateinit var titleEditText : EditText
    lateinit var descriptionEditText : EditText
    lateinit var passedNote : Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        titleEditText = findViewById<EditText>(R.id.editNoteTitleEditText)
        descriptionEditText = findViewById<EditText>(R.id.editNoteDescriptionEditText)
        var updateButtonSubmit = findViewById<Button>(R.id.updateNoteSubmitButton)
        var deletedText = findViewById<TextView>(R.id.editNoteUppernoteTextView)

        passedNote = intent.extras!!.getParcelable<Note>("NOTE_TO_EDIT")!!


        titleEditText.setText(passedNote.noteTitle.toString())
        descriptionEditText.setText(passedNote.noteDescription.toString())

        updateButtonSubmit.setOnClickListener {
            updateNote()
        }

        deletedText.setOnClickListener {
            Toast.makeText(this,"DA",Toast.LENGTH_SHORT).show()
            deleteNote()

        }

    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") {_,_ ->
            mNoteViewModel.deleteNote(passedNote)
            Toast.makeText(this,"Note deleted",Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton("No") {_,_ ->}
        builder.setTitle("Delete " + titleEditText.text.toString())
        builder.setMessage("Are you sure you want to delete this Note")
        builder.create().show()
    }

    private fun updateNote() {
        val noteTitle = titleEditText.text.toString()
        var noteDescription = descriptionEditText.text.toString()
        var updatedNote = Note(passedNote.id,noteTitle,noteDescription)
        if(inputCheck(noteTitle,noteDescription)){
            mNoteViewModel.updateNote(updatedNote)
            finish()
        }
    }


    private fun inputCheck(titleText: String, descriptionText: String): Boolean{
        return !(TextUtils.isEmpty(titleText) && TextUtils.isEmpty(descriptionText))
    }
}