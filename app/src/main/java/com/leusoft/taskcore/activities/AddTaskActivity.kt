package com.leusoft.taskcore.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.leusoft.taskcore.R
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.viewmodel.NoteViewModel
import com.leusoft.taskcore.viewmodel.TaskViewModel
import org.w3c.dom.Text
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    lateinit var taskDescription: EditText
    lateinit var taskTitle: EditText
    var taskState: Int = 0
    private lateinit var mTaskViewModel: TaskViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskTitle = findViewById<EditText>(R.id.addTaskTitleEditText)
        taskDescription = findViewById<EditText>(R.id.addTaskDescriptionEditText)
        var taskRadioGroup = findViewById<RadioGroup>(R.id.addTaskRadioGroup)
        var addTaskSubmitButton = findViewById<Button>(R.id.addTaskSubmitButton)


        addTaskSubmitButton.setOnClickListener {


            var selectedRadioGroup = taskRadioGroup!!.checkedRadioButtonId

            when(selectedRadioGroup)
            {
                R.id.radioButton -> taskState = 1
                R.id.radioButton2 -> taskState = 2
                R.id.radioButton3 -> taskState = 3
            }

            insertTaskToDatabase()

        }

    }

    private fun insertTaskToDatabase() {
        val titleText = taskTitle.text.toString()
        val descriptionText = taskDescription.text.toString()

        if (inputCheck(titleText, descriptionText)) {
            val task = Task(0, titleText, descriptionText,taskState)
            mTaskViewModel.addTask(task)
            Toast.makeText(this, "Successfully added!", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "Please fill out all fields corectly.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(titleText: String, descriptionText: String): Boolean {
        return !(TextUtils.isEmpty(titleText) && TextUtils.isEmpty(descriptionText))
    }



}