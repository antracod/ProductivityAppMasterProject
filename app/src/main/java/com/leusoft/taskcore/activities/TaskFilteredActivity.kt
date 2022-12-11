package com.leusoft.taskcore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leusoft.taskcore.R
import com.leusoft.taskcore.adapters.DashboardRecyclerAdapter
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.viewmodel.TaskViewModel


class TaskFilteredActivity : AppCompatActivity() {

    var passedFilter : Int = 1
    private lateinit var mTaskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_filtered)

        passedFilter = intent.extras!!.getInt("task_filter")

        val adapter = DashboardRecyclerAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.filteredRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // UserViewModel
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        when (passedFilter) {
            1 -> {
                mTaskViewModel.readFilteredDataStateOne.observe(this, Observer { task ->
                    adapter.setData(task)
                })
            }
            2 -> {
                mTaskViewModel.readFilteredDataStateTwo.observe(this, Observer { task ->
                    adapter.setData(task)
                })
            }
            else -> {
                mTaskViewModel.readFilteredDataStateThree.observe(this, Observer { task ->
                    adapter.setData(task)
                })
            }
        }





    }



}