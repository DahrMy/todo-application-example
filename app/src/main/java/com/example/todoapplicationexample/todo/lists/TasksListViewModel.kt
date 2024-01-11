package com.example.todoapplicationexample.todo.lists

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TasksListViewModel(
    private val repository: TasksListRepository,
    private val disposable: CompositeDisposable
) : ViewModel() {



}