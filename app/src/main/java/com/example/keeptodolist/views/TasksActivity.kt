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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.adapters.TasksAdapter
import com.example.keeptodolist.data.Task
import com.example.keeptodolist.data.TasksList
import com.example.keeptodolist.utilities.HandleTaskOnClick
import com.example.keeptodolist.viewmodels.KeepViewModel
import de.hdodenhof.circleimageview.CircleImageView

class TasksActivity : AppCompatActivity(), View.OnClickListener {
    private var listColor: Int = 0
    private lateinit var keepViewModel: KeepViewModel
    private lateinit var addTaskEditText: EditText
    lateinit var listTextView: TextView
    private var listId = 0
    lateinit var listName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        listId = intent.getIntExtra("listId", 0)
        listName = intent.getStringExtra("listName").toString()
        listColor = intent.getIntExtra("listColor", 0)

        listTextView = findViewById<TextView>(R.id.listTitle_textView)
        val recyclerView = findViewById<RecyclerView>(R.id.tasks_recyclerView)
        val completedRecyclerView = findViewById<RecyclerView>(R.id.completedTasks_recyclerView)
        val completeTextView = findViewById<TextView>(R.id.completeTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        val addButton = findViewById<ImageView>(R.id.addTask_button)
        val deleteButton = findViewById<ImageView>(R.id.delete_button)
        var tasksAdapter: TasksAdapter
        var completedTasksAdapter: TasksAdapter
        addTaskEditText = findViewById(R.id.addTask_editText)

        backButton.setOnClickListener(this)
        addButton.setOnClickListener(this)
        deleteButton.setOnClickListener(this)
        listTextView.setOnClickListener(this)

        listTextView.text = listName

        keepViewModel = ViewModelProvider(this)[KeepViewModel::class.java]

        completedRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        keepViewModel.getUncompletedTasks(listId).observe(this, { tasks ->
            tasksAdapter = TasksAdapter(tasks, object : HandleTaskOnClick {
                override fun onClickItemListener(task: Task) {
                    keepViewModel.updateTask(task)
                }

                override fun onClickListener(task: Task) {
                    showEditTaskDialog(task)
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

                override fun onClickListener(task: Task) {
                    showEditTaskDialog(task)
                }
            })
            completedRecyclerView.adapter = completedTasksAdapter
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backButton -> finish()
            R.id.delete_button -> {
                showConfirmDialog(isTask = false, listName, listId)
            }
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
            R.id.listTitle_textView -> showEditListDialog()
        }
    }

    private fun showConfirmDialog(isTask: Boolean, title: String, id: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.confirm_item)

        val confirmText = dialog.findViewById<TextView>(R.id.confirm_textView)
        confirmText.text = getString(R.string.confirm_message, title)

        val deleteButton = dialog.findViewById(R.id.delete_button) as Button
        val cancelButton = dialog.findViewById(R.id.cancel_button) as Button
        deleteButton.setOnClickListener {
            dialog.dismiss()
            if (!isTask) {
                keepViewModel.deleteList(id)
                finish()
            } else
                keepViewModel.deleteTask(id, listId)

        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showEditTaskDialog(task: Task) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.task_dialog_item)

        val taskText = dialog.findViewById<TextView>(R.id.taskTitle_editText)
        taskText.text = task.name
        val deleteButton = dialog.findViewById<ImageView>(R.id.delete_button)
        val updateButton = dialog.findViewById(R.id.update_button) as Button
        val cancelButton = dialog.findViewById(R.id.cancel_button) as Button
        updateButton.setOnClickListener {
            if (taskText.text.toString().trim().isNotEmpty()) {
                dialog.dismiss()
                task.name = taskText.text.toString().trim()
                keepViewModel.updateTask(task)
            }
        }
        deleteButton.setOnClickListener {
            dialog.dismiss()
            showConfirmDialog(true, task.name, task.id)
        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showEditListDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_list_item)

        val listTitleEditText = dialog.findViewById(R.id.listTitle_editText) as EditText
        listTitleEditText.setText(listName)

        val c1 = dialog.findViewById<CircleImageView>(R.id.c1)
        val c2 = dialog.findViewById<CircleImageView>(R.id.c2)
        val c3 = dialog.findViewById<CircleImageView>(R.id.c3)
        val c4 = dialog.findViewById<CircleImageView>(R.id.c4)
        val c5 = dialog.findViewById<CircleImageView>(R.id.c5)
        val c6 = dialog.findViewById<CircleImageView>(R.id.c6)
        val listIconImage = dialog.findViewById<CircleImageView>(R.id.listIcon_imageView)
        listIconImage.borderColor = listColor

        c1.setOnClickListener {
            listIconImage.borderColor = c1.borderColor
        }
        c2.setOnClickListener {
            listIconImage.borderColor = c2.borderColor
        }
        c3.setOnClickListener {
            listIconImage.borderColor = c3.borderColor
        }
        c4.setOnClickListener {
            listIconImage.borderColor = c4.borderColor
        }
        c5.setOnClickListener {
            listIconImage.borderColor = c5.borderColor
        }
        c6.setOnClickListener {
            listIconImage.borderColor = c6.borderColor
        }

        val saveButton = dialog.findViewById(R.id.createListButton) as Button
        val cancelButton = dialog.findViewById(R.id.cancel_button) as Button
        saveButton.text = getString(R.string.save)
        saveButton.setOnClickListener {
            if (listTitleEditText.text.toString().trim().isNotEmpty()) {
                keepViewModel.updateList(
                    TasksList(
                        listId,
                        listTitleEditText.text.toString().trim(),
                        listIconImage.borderColor,
                        0
                    )
                )
                dialog.dismiss()
                listTextView.text = listTitleEditText.text.toString().trim()
            }

        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}