package com.example.keeptodolist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeepDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addList(tasksList: TasksList)

    @Query("SELECT * FROM task_table WHERE listId = :listId and state=0")
    fun getUncompletedTasks(listId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM lists_table ORDER By id DESC")
    fun getAllLists(): LiveData<List<TasksList>>

    @Update
    suspend fun updateTask(task: Task)

    @Update
    suspend fun updateList(list: TasksList)

    @Query("Update lists_table set count = count+1 where id=:listId")
    suspend fun updateListCount(listId: Int)
    @Query("SELECT * FROM task_table WHERE listId = :listId and state=1")
    fun getCompletedTasks(listId: Int): LiveData<List<Task>>
}