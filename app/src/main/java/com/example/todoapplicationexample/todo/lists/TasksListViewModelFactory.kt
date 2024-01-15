package com.example.todoapplicationexample.todo.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TasksListViewModelFactory(
    private val model: TasksListModel,
    private val disposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            return TasksListViewModel(model, disposable) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}