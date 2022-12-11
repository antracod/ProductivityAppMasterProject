package com.leusoft.taskcore.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView

import com.leusoft.taskcore.R
import com.leusoft.taskcore.activities.EditNoteActivity
import com.leusoft.taskcore.activities.EditTaskActivity
import com.leusoft.taskcore.data.Task
import com.leusoft.taskcore.viewmodel.TaskViewModel
import java.security.AccessController.getContext


class DashboardRecyclerAdapter(context: ViewModelStoreOwner) :

    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardRecyclerViewHolder>() {

    private var taskList = emptyList<Task>()
    val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_recycler_list_item,parent,false)
        return DashboardRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardRecyclerViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.taskTitle.text = currentItem.taskTitle

        if(taskList[position].taskState == 3)
        {
            holder.taskProgress.progress = 100
            holder.checkBoxImage.setColorFilter(Color.GREEN)

        } else if(taskList[position].taskState == 2)
        {
            holder.taskProgress.progress = 50
            holder.checkBoxImage.setColorFilter(Color.GRAY)
        }
        else
        {
            holder.taskProgress.progress = 0
            holder.checkBoxImage.setColorFilter(Color.GRAY)
        }
        holder.checkBoxImage.setOnClickListener {

            if(holder.taskProgress.progress == 100)
            {
                holder.taskProgress.progress = 0
                holder.checkBoxImage.setColorFilter(Color.GRAY)


                val mTaskViewModel : TaskViewModel = ViewModelProvider(mContext).get(TaskViewModel::class.java)
                mTaskViewModel.updateTask(Task(currentItem.id,currentItem.taskTitle,currentItem.taskDescription,1))

            }
            else
            {
                holder.taskProgress.progress = 100
                holder.checkBoxImage.setColorFilter(Color.GREEN)

                val mTaskViewModel : TaskViewModel = ViewModelProvider(mContext).get(TaskViewModel::class.java)
                mTaskViewModel.updateTask(Task(currentItem.id,currentItem.taskTitle,currentItem.taskDescription,3))
            }

        }

        holder.taskTitle.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditTaskActivity::class.java)
            intent.putExtra("TASK_TO_EDIT", taskList[position])
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class DashboardRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val taskTitle : TextView = itemView.findViewById<TextView>(R.id.recyclerViewDashboardNoteTitle)
        var taskProgress: ProgressBar = itemView.findViewById(R.id.taskRowProgressBar)
        var checkBoxImage : ImageView = itemView.findViewById<ImageView>(R.id.imageViewRecyclerDashboardCheckBox)
    }

    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}