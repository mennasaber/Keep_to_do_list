package com.example.keeptodolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists_table")
data class TasksList(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val color:String)