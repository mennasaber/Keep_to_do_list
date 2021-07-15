package com.example.keeptodolist.data

import androidx.lifecycle.LiveData

class KeepRepository(private val keepDao: KeepDao) {
    suspend fun addList(tasksList: TasksList) {
        keepDao.addList(tasksList)
    }

    suspend fun addTask(task: Task) {
        keepDao.addTask(task)
    }

    fun getAllTasksList(): LiveData<List<TasksList>> {
        return keepDao.getAllLists()
    }

    fun getAllTasks(listId: Int): LiveData<List<Task>> {
        return keepDao.getAllTasks(listId)
    }
}