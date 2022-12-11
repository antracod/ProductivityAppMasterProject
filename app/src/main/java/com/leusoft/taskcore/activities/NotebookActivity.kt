package com.leusoft.taskcore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leusoft.taskcore.R
import com.leusoft.taskcore.adapters.NoteAdapter
import com.leusoft.taskcore.viewmodel.NoteViewModel

class NotebookActivity : AppCompatActivity() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notebook)

        val adapter = NoteAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.notebookRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // UserViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(this, Observer { note ->
            adapter.setData(note)
        })


    }



}