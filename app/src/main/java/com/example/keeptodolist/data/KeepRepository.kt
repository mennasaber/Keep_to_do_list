package com.example.keeptodolist.data

import androidx.lifecycle.LiveData

class KeepRepository(private val keepDao: KeepDao) {
    suspend fun addList(tasksList: TasksList) {
        keepDao.addList(tasksList)
    }

    suspend fun addTask(task: Task) {
        keepDao.updateListCount(task.listId)
        keepDao.addTask(task)
    }

    fun getAllTasksList(): LiveData<List<TasksList>> {
        return keepDao.getAllLists()
    }

    fun getUncompletedTasks(listId: Int): LiveData<List<Task>> {
        return keepDao.getUncompletedTasks(listId)
    }
    suspend fun updateTask(task: Task){
        keepDao.updateTask(task)
    }
    suspend fun updateList(list: TasksList){
        keepDao.updateList(list)
    }

    fun getCompletedTasks(listId: Int): LiveData<List<Task>> {
       return keepDao.getCompletedTasks(listId)
    }
}