package com.example.keeptodolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeepDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addList(tasksList: TasksList)
    @Query("SELECT * FROM task_table WHERE listId = :listId")
    fun getAllTasks(listId:Int):LiveData<List<Task>>
    @Query("SELECT * FROM lists_table ORDER By id DESC")
    fun getAllLists():LiveData<List<TasksList>>
    @Update
    fun updateTask(task: Task)
    @Update
    fun updateList(list: TasksList)
}