package com.example.keeptodolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.data.Task
import com.example.keeptodolist.utilities.HandleTaskOnClick

class TasksAdapter(private val tasksList: List<Task>, private val handleTask: HandleTaskOnClick) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskCheckBox: CheckBox = view.findViewById(R.id.task_checkBox)
        val taskTitleTextView: TextView = view.findViewById(R.id.taskTitle_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskTitleTextView.text = tasksList[position].name
        holder.taskCheckBox.isChecked = tasksList[position].state
        holder.taskCheckBox.setOnClickListener {
            tasksList[position].state = !tasksList[position].state
            handleTask.onClickItemListener(tasksList[position])
        }
        holder.taskTitleTextView.setOnClickListener {
            handleTask.onClickListener(tasksList[position])
        }
    }

    override fun getItemCount(): Int {
        return tasksList.count()
    }
}