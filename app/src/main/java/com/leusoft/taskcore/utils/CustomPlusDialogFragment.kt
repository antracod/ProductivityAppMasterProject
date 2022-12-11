package com.leusoft.taskcore.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.leusoft.taskcore.activities.AddEventActivity
import com.leusoft.taskcore.R
import com.leusoft.taskcore.activities.AddNoteActivity
import com.leusoft.taskcore.activities.AddTaskActivity

class CustomPlusDialogFragment(private val popupContext: Context) :DialogFragment(){

    override fun onCreateView(inflater:LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?):View?{



        var rootViewPopup : View = inflater.inflate(R.layout.popup_bottom_bar_plus,container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        var popupNotesButton = rootViewPopup.findViewById<Button>(R.id.popupPlusNotes)
        var popupTaskButton = rootViewPopup.findViewById<Button>(R.id.popupPlusTask)
        var popupEventButton = rootViewPopup.findViewById<Button>(R.id.popupPlusEvent)

        popupNotesButton.setOnClickListener {
            val intent = Intent(popupContext, AddNoteActivity::class.java)
            startActivity(intent)
            dismiss()
        }

        popupTaskButton.setOnClickListener {
            val intent = Intent(popupContext, AddTaskActivity::class.java)
            startActivity(intent)
            dismiss()
        }

        popupEventButton.setOnClickListener {
            val intent = Intent(popupContext, AddEventActivity::class.java)
            startActivity(intent)
            dismiss()
        }
        return rootViewPopup

    }



}