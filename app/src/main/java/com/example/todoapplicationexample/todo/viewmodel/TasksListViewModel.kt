package com.example.todoapplicationexample.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val model: TasksListModel
) : ViewModel() {

    private val coroutineContext = Job() + Dispatchers.IO

    private val tasksFlow = MutableSharedFlow<List<Task>>()

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    fun getListFlow(): Flow<List<Task>> = tasksFlow

    fun emitTasksList(status: TaskStatus) {
        viewModelScope.launch(coroutineContext) {
            val result = model.list.filter {
                it.status == status
            }

            tasksFlow.emit(result)
        }
    }

    suspend fun loadList() {
        model.list = model.getTasks().toMutableList()
    }

    fun uploadItemToList(task: Task) {
        model.list.add(0, task)
        viewModelScope.launch(coroutineContext) {
            model.uploadListToDB()
        }

    }

}