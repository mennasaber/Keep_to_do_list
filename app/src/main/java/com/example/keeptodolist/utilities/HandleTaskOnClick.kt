package com.example.keeptodolist.utilities

import com.example.keeptodolist.data.Task
import com.example.keeptodolist.data.TasksList

interface HandleTaskOnClick {
     fun onClickItemListener(task: Task)
}