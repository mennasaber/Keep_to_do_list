package com.example.keeptodolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var state: Boolean,
    val listId: Int)