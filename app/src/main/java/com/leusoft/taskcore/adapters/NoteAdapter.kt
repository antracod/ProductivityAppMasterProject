package com.leusoft.taskcore.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.leusoft.taskcore.R
import com.leusoft.taskcore.activities.EditNoteActivity
import com.leusoft.taskcore.data.Note


class NoteAdapter():
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var noteList = emptyList<Note>()

    override fun onCreateViewHolder(parinte: ViewGroup, tipView: Int): NoteViewHolder {
         val aspectVizualizatorItem = LayoutInflater.from(parinte.context).inflate(R.layout.note_row,parinte,false)
        return NoteViewHolder(aspectVizualizatorItem)
    }


    override fun onBindViewHolder(containerViewParam: NoteViewHolder, pozitie_item: Int) {
        val itemCurentDePopulat = noteList[pozitie_item]
        containerViewParam.noteTitle.text = itemCurentDePopulat.noteTitle.toString()
        containerViewParam.noteDescription.text = itemCurentDePopulat.noteDescription.toString()
        containerViewParam.rowLayout!!.setOnClickListener {
            val intent = Intent(containerViewParam.itemView.context, EditNoteActivity::class.java)
            intent.putExtra("NOTE_TO_EDIT", noteList[pozitie_item])
            containerViewParam.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class NoteViewHolder (aspectItem: View) : RecyclerView.ViewHolder(aspectItem){
        val noteTitle : TextView= aspectItem.findViewById<TextView>(R.id.noteRowTitle)
        val noteDescription : TextView = aspectItem.findViewById<TextView>(R.id.noteRowDescription)
        val rowLayout : ConstraintLayout? = aspectItem.findViewById<ConstraintLayout>(R.id.noteRowConstraintLayout)


    }

    fun setData(note: List<Note>){
        this.noteList = note
        notifyDataSetChanged()
    }
}