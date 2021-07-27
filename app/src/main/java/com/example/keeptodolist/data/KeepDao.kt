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

    @Query("SELECT * FROM task_table WHERE listId = :listId and state=1")
    fun getCompletedTasks(listId: Int): LiveData<List<Task>>

    @Query("SELECT count(*) FROM task_table WHERE listId = :listId")
    fun getTasksCount(listId: Int): LiveData<Int>

    @Update
    suspend fun updateTask(task: Task)

    @Query("update lists_table set name= :name_ , color = :color_ where id=:id_")
    suspend fun updateList(id_: Int, name_: String, color_: Int)

    @Query("Update lists_table set count = count+1 where id=:listId")
    suspend fun updateListCount(listId: Int)

    @Query("Update lists_table set count = count-1 where id=:listId")
    suspend fun decreaseListCount(listId: Int)

    @Query("delete from lists_table where id=:listId")
    suspend fun deleteList(listId: Int)

    @Query("delete from task_table where id=:listId")
    suspend fun deleteTasksOfList(listId: Int)

    @Query("delete from task_table where id=:id")
    suspend fun deleteTask(id: Int)


}