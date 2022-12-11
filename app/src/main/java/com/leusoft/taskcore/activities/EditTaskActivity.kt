package com.leusoft.taskcore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.leusoft.taskcore.R
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.viewmodel.TaskViewModel


class EditTaskActivity : AppCompatActivity() {

    private lateinit var mTaskViewModel: TaskViewModel

    lateinit var titleEditText : EditText
    lateinit var descriptionEditText : EditText
    lateinit var passedTask : Task
    var taskState = 1
    lateinit var taskRadioGroupState: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)


        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        titleEditText = findViewById<EditText>(R.id.editTaskTitleEditText)
        descriptionEditText = findViewById<EditText>(R.id.editTaskDescriptionEditText)
        var updateButtonSubmit = findViewById<Button>(R.id.editTaskSubmitButton)
        var deletedText = findViewById<TextView>(R.id.editTaskAdnoteTextView3)
        taskRadioGroupState = findViewById<RadioGroup>(R.id.editTaskRadioGroup)


        passedTask = intent.extras!!.getParcelable<Task>("TASK_TO_EDIT")!!


        titleEditText.setText(passedTask.taskTitle.toString())
        descriptionEditText.setText(passedTask.taskDescription.toString())

        updateButtonSubmit.setOnClickListener {


            updateTask()
        }

        deletedText.setOnClickListener {
            Toast.makeText(this,"DA",Toast.LENGTH_SHORT).show()
            deleteTask()

        }

    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") {_,_ ->
            mTaskViewModel.deleteTask(passedTask)
            Toast.makeText(this,"Task deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
        builder.setNegativeButton("No") {_,_ ->}
        builder.setTitle("Delete " + titleEditText.text.toString())
        builder.setMessage("Are you sure you want to delete this Task")
        builder.create().show()
    }

    private fun updateTask() {
        val taskTitle = titleEditText.text.toString()
        var taskDescription = descriptionEditText.text.toString()
        var selectedRadioGroup = taskRadioGroupState!!.checkedRadioButtonId

        when(selectedRadioGroup)
        {
            R.id.editradioButton -> taskState = 1
            R.id.editradioButton2 -> taskState = 2
            R.id.editradioButton3 -> taskState = 3
        }
        var updatedTask = Task(passedTask.id,taskTitle,taskDescription,taskState)
        if(inputCheck(taskTitle,taskDescription)){
            mTaskViewModel.updateTask(updatedTask)
            finish()
        }
    }


    private fun inputCheck(titleText: String, descriptionText: String): Boolean{
        return !(TextUtils.isEmpty(titleText) && TextUtils.isEmpty(descriptionText))
    }
}