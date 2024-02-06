package com.example.todoapplicationexample.todo.viewmodel

import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskUtils

class TasksListModel {

    var list: MutableList<Task> = mutableListOf()

    suspend fun getTasks(): List<Task> = TaskUtils.generateSimpleList() // Imitation loading from DB, because suspend is it
    suspend fun uploadListToDB() {
        // Imitation uploading to DB, because suspend
    }

}