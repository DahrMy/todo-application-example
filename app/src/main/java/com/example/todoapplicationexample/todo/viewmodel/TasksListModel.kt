package com.example.todoapplicationexample.todo.viewmodel

import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskUtils

class TasksListModel {

    var list: List<Task> = listOf()

    suspend fun getTasks(): List<Task> = TaskUtils.generateSimpleList() // Imitation loading from DB, because suspend is it

}