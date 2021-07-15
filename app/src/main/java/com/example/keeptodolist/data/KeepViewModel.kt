package com.example.keeptodolist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeepViewModel(application: Application) : AndroidViewModel(application) {
    var lists: LiveData<List<TasksList>>
    var repository: KeepRepository

    init {
        val dao = KeepDatabase.getDatabase(application).keepDao()
        repository = KeepRepository(dao)
        lists = repository.getAllTasksList()
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

    fun getTasks(listId: Int) :LiveData<List<Task>>{
        return repository.getAllTasks(listId)
    }
}