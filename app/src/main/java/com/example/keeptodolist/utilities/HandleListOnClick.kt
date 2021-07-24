package com.example.keeptodolist.utilities

import com.example.keeptodolist.data.TasksList
import java.util.*

interface HandleListOnClick {
    fun onClickItemListener(list: TasksList)
}