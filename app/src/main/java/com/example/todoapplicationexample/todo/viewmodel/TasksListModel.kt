package com.example.todoapplicationexample.todo.viewmodel

import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskUtils

class TasksListModel {

    fun getTasks(): List<Task> = TaskUtils.generateSimpleList()

}