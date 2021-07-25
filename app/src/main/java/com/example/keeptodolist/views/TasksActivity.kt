package com.example.keeptodolist.views

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.adapters.TasksAdapter
import com.example.keeptodolist.data.Task
import com.example.keeptodolist.utilities.HandleTaskOnClick
import com.example.keeptodolist.viewmodels.KeepViewModel
import de.hdodenhof.circleimageview.CircleImageView

class TasksActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var keepViewModel: KeepViewModel
    private lateinit var addTaskEditText: EditText
    private var listId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        listId = intent.getIntExtra("listId", 0)
        val listName = intent.getStringExtra("listName")
        val listTextView = findViewById<TextView>(R.id.listTitle_textView)
        val listColorIcon = findViewById<CircleImageView>(R.id.listIcon_imageView)
        val recyclerView = findViewById<RecyclerView>(R.id.tasks_recyclerView)
        val completedRecyclerView = findViewById<RecyclerView>(R.id.completedTasks_recyclerView)
        val completeTextView = findViewById<TextView>(R.id.completeTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        val addButton = findViewById<ImageView>(R.id.addTask_button)
        var tasksAdapter: TasksAdapter
        var completedTasksAdapter: TasksAdapter
        addTaskEditText = findViewById(R.id.addTask_editText)

        backButton.setOnClickListener(this)
        addButton.setOnClickListener(this)

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
            if (tasks.count() == 0)
                completeTextView.visibility = View.GONE
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
        when (v?.id) {
            R.id.backButton -> finish()
            R.id.addTask_button -> {
                if (addTaskEditText.text.toString().trim().isNotEmpty()) {
                    keepViewModel.addTask(
                        Task(
                            0,
                            addTaskEditText.text.toString().trim(),
                            false,
                            listId
                        )
                    )
                    addTaskEditText.text.clear()
                }
            }
        }
    }


}