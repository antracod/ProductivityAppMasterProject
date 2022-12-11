package com.leusoft.taskcore.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.leusoft.taskcore.R
import com.leusoft.taskcore.adapters.DashboardRecyclerAdapter
import com.leusoft.taskcore.adapters.NoteAdapter
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.datatypes.TaskDataClass
import com.leusoft.taskcore.utils.CalendarUtils
import com.leusoft.taskcore.utils.CustomPlusDialogFragment
import com.leusoft.taskcore.viewmodel.NoteViewModel
import com.leusoft.taskcore.viewmodel.TaskViewModel
import java.time.LocalDate

class DashboardActivity : AppCompatActivity() {

    lateinit var mDialog : CustomPlusDialogFragment;

    lateinit var aspectNavigatieInferioara: BottomNavigationView;

    private lateinit var dashboardRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Task>
    lateinit var taskTitle : Array<String>

    private lateinit var mTaskViewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val goToNotebook = findViewById<TextView>(R.id.dashboardGoToNotebookTextView)
        goToNotebook.setOnClickListener {

            val intentieCustom = Intent(this,NotebookActivity::class.java)
            startActivity(intentieCustom)
        }

        val smallPlus = findViewById<ImageView>(R.id.smallAddTaskImageView)
        smallPlus.setOnClickListener {
            var intentieCustom = Intent(this, AddTaskActivity::class.java)
            startActivity(intentieCustom)

        }


        val goToStarted = findViewById<TextView>(R.id.openFilterStartedTextView)
        val goToOngoing = findViewById<TextView>(R.id.openFilterOngoingTextView)
        val goToCompleted = findViewById<TextView>(R.id.openFilterCompletedTextView)

        goToStarted.setOnClickListener {
            val intentieCustom = Intent(this, TaskFilteredActivity::class.java)
            intentieCustom.putExtra("task_filter",1)
            startActivity(intentieCustom)
        }
        goToOngoing.setOnClickListener {
            val intentieCustom = Intent(this, TaskFilteredActivity::class.java)
            intentieCustom.putExtra("task_filter",2)
            startActivity(intentieCustom)
        }
        goToCompleted.setOnClickListener {
            val intentieCustom = Intent(this, TaskFilteredActivity::class.java)
            intentieCustom.putExtra("task_filter",3)
            startActivity(intentieCustom)
        }




        CalendarUtils.selectedDate = LocalDate.now();
        aspectNavigatieInferioara = findViewById(R.id.bottom_navigator)
        aspectNavigatieInferioara.selectedItemId = R.id.dashboard

        aspectNavigatieInferioara.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.plus -> {
                    mDialog = CustomPlusDialogFragment(this)
                    mDialog.show(supportFragmentManager,"customDialog")
                }
                R.id.dashboard -> {

                }
                R.id.calendar -> {
                    val intentieCustom = Intent(this, DailyCalendarActivity::class.java)
                    intentieCustom.flags = intentieCustom.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intentieCustom)
                    overridePendingTransition(0,0)
                }
                R.id.social -> {
                    val intentieCustom = Intent(this, SocialActivity::class.java)
                    intentieCustom.flags = intentieCustom.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intentieCustom)
                    overridePendingTransition(0,0)
                }
                R.id.profile -> {
                    val intentieCustom = Intent(this, SettingsActivity::class.java)
                    startActivity(intentieCustom)
                    overridePendingTransition(0,0)
                }

            }
            true
        }


        val adapter = DashboardRecyclerAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.dashboardRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // UserViewModel
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        mTaskViewModel.readAllData.observe(this, Observer { task ->
            adapter.setData(task)
        })

        val filterTaskTextViewDashboardTV = findViewById<TextView>(R.id.filterTaskTextViewDashboard)
        filterTaskTextViewDashboardTV.setOnClickListener {
            if(filterTaskTextViewDashboardTV.text == "All Tasks")
            {
                mTaskViewModel.readFilteredDataStateOne.observe(this, Observer { task ->
                    adapter.setData(task)
                })
                filterTaskTextViewDashboardTV.text = "Fresh"
            }
            else
            {
                mTaskViewModel.readAllData.observe(this, Observer { task ->
                    adapter.setData(task)
                })
                filterTaskTextViewDashboardTV.text = "All Tasks"
            }
        }





    }


}


