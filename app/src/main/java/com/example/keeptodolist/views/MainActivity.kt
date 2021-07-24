package com.example.keeptodolist.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.keeptodolist.R
import com.example.keeptodolist.adapters.ListsAdapter
import com.example.keeptodolist.viewmodels.KeepViewModel
import com.example.keeptodolist.data.TasksList
import com.example.keeptodolist.utilities.HandleListOnClick


class MainActivity : AppCompatActivity() {
    private lateinit var keepViewModel: KeepViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keepViewModel = ViewModelProvider(this)[KeepViewModel::class.java]
        val recyclerView = findViewById<RecyclerView>(R.id.lists_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var listsAdapter: ListsAdapter
        keepViewModel.getLists().observe(this, { lists ->
            listsAdapter = ListsAdapter(lists, object : HandleListOnClick {
                override fun onClickItemListener(list:TasksList) {
                    val intent = Intent(this@MainActivity, TasksActivity::class.java).apply {
                        putExtra("listId", list.id)
                        putExtra("listName", list.name)
                        putExtra("listColor",list.color)
                    }
                    startActivity(intent)
                }
            })
            recyclerView.adapter = listsAdapter
        })
    }

}