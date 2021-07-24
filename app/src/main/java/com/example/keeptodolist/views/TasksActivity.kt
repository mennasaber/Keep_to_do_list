package com.example.keeptodolist.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.adapters.TasksAdapter
import com.example.keeptodolist.viewmodels.KeepViewModel
import com.example.keeptodolist.data.Task
import com.example.keeptodolist.utilities.HandleTaskOnClick
import de.hdodenhof.circleimageview.CircleImageView

class TasksActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var keepViewModel: KeepViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val listId = intent.getIntExtra("listId", 0)
        val listName = intent.getStringExtra("listName")
        val listColor = intent.getIntExtra("listColor", 0)
        val listTextView = findViewById<TextView>(R.id.listTitle_textView)
        val listColorIcon = findViewById<CircleImageView>(R.id.listIcon_imageView)
        val recyclerView = findViewById<RecyclerView>(R.id.tasks_recyclerView)
        val completedRecyclerView = findViewById<RecyclerView>(R.id.completedTasks_recyclerView)
        val completeTextView =  findViewById<TextView>(R.id.completeTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        var tasksAdapter: TasksAdapter
        var completedTasksAdapter: TasksAdapter

        backButton.setOnClickListener(this)
        listColorIcon.borderColor = ContextCompat.getColor(this, listColor)
        listTextView.text = listName
        keepViewModel = ViewModelProvider(this)[KeepViewModel::class.java]
        completedRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        keepViewModel.getUncompletedTasks(listId).observe(this, { tasks ->
            tasksAdapter = TasksAdapter(tasks, object : HandleTaskOnClick {
                override fun onClickItemListener(task: Task) {
                    keepViewModel.updateTask(task)
                }
            })
            recyclerView.adapter = tasksAdapter
        })
        keepViewModel.getCompletedTasks(listId).observe(this, { tasks ->
            if(tasks.count()==0)
               completeTextView.visibility= View.GONE
            else
                completeTextView.visibility = View.VISIBLE
            completedTasksAdapter = TasksAdapter(tasks, object : HandleTaskOnClick {
                override fun onClickItemListener(task: Task) {
                    keepViewModel.updateTask(task)
                }
            })
            completedRecyclerView.adapter = completedTasksAdapter
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backButton -> finish()
        }
    }
}