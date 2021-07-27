package com.example.keeptodolist.views

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.adapters.ListsAdapter
import com.example.keeptodolist.data.TasksList
import com.example.keeptodolist.utilities.HandleListOnClick
import com.example.keeptodolist.viewmodels.KeepViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {
    private lateinit var keepViewModel: KeepViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keepViewModel = ViewModelProvider(this)[KeepViewModel::class.java]
        val recyclerView = findViewById<RecyclerView>(R.id.lists_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val addListButton = findViewById<FloatingActionButton>(R.id.addListButton)
        val emptyImageView = findViewById<ImageView>(R.id.empty_imageView)
        addListButton.setOnClickListener {
            showDialog()
        }
        var listsAdapter: ListsAdapter
        keepViewModel.getLists().observe(this, { lists ->
            if (lists.isEmpty())
                emptyImageView.visibility = View.VISIBLE
            else
                emptyImageView.visibility = View.GONE
            listsAdapter = ListsAdapter(lists, object : HandleListOnClick {
                override fun onClickItemListener(list: TasksList) {
                    val intent = Intent(this@MainActivity, TasksActivity::class.java).apply {
                        putExtra("listId", list.id)
                        putExtra("listName", list.name)
                        putExtra("listColor", list.color)
                    }
                    startActivity(intent)
                }
            })
            recyclerView.adapter = listsAdapter
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_list_item)
        val listTitleEditText = dialog.findViewById(R.id.listTitle_editText) as EditText

        val c1 = dialog.findViewById<CircleImageView>(R.id.c1)
        val c2 = dialog.findViewById<CircleImageView>(R.id.c2)
        val c3 = dialog.findViewById<CircleImageView>(R.id.c3)
        val c4 = dialog.findViewById<CircleImageView>(R.id.c4)
        val c5 = dialog.findViewById<CircleImageView>(R.id.c5)
        val c6 = dialog.findViewById<CircleImageView>(R.id.c6)
        val listIconImage = dialog.findViewById<CircleImageView>(R.id.listIcon_imageView)
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
        val addButton = dialog.findViewById(R.id.createListButton) as Button
        val cancelButton = dialog.findViewById(R.id.cancel_button) as Button
        addButton.setOnClickListener {
            if (listTitleEditText.text.toString().trim().isNotEmpty()) {
                dialog.dismiss()
                keepViewModel.addList(
                    TasksList(
                        0,
                        listTitleEditText.text.toString().trim(),
                        listIconImage.borderColor,
                        0
                    )
                )
            }

        }
        cancelButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}