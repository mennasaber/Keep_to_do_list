package com.example.keeptodolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.keeptodolist.data.KeepViewModel
import com.example.keeptodolist.data.Task

class MainActivity : AppCompatActivity() {
    private lateinit var keepViewModel: KeepViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keepViewModel = ViewModelProvider(this)[KeepViewModel::class.java]
        keepViewModel.addTask(Task(0, "Lecture-1", false, 1))
        keepViewModel.addTask(Task(0, "Lecture-2", false, 2))
    }
}