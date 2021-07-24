package com.example.keeptodolist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.keeptodolist.data.KeepDatabase
import com.example.keeptodolist.data.KeepRepository
import com.example.keeptodolist.data.Task
import com.example.keeptodolist.data.TasksList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeepViewModel(application: Application) : AndroidViewModel(application) {
    var repository: KeepRepository

    init {
        val dao = KeepDatabase.getDatabase(application).keepDao()
        repository = KeepRepository(dao)
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun addList(tasksList: TasksList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addList(tasksList)
        }
    }

    fun getUncompletedTasks(listId: Int): LiveData<List<Task>> {
        return repository.getUncompletedTasks(listId)
    }

    fun getLists(): LiveData<List<TasksList>> {
        return repository.getAllTasksList()
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun updateList(list: TasksList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateList(list)
        }
    }

    fun getCompletedTasks(listId: Int): LiveData<List<Task>> {
        return repository.getCompletedTasks(listId)
    }
}