package com.example.todoapplicationexample.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TasksListViewModelFactory(
    private val model: TasksListModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            return TasksListViewModel(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}