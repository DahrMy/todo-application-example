package com.example.todoapplicationexample.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val model: TasksListModel
) : ViewModel() {

    private val coroutineContext = Job() + Dispatchers.IO

    private val tasksMutableFlow = MutableSharedFlow<List<Task>>()
    val tasksFlow: SharedFlow<List<Task>> = tasksMutableFlow

    val statusFilterLiveData = MutableLiveData(TaskStatus.IN_PROGRESS)

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    fun startListLoading() {
        viewModelScope.launch(coroutineContext) {

            model.getTaskEntities().collect { list ->
                val tasksList = mutableListOf<Task>()

                list.map {
                    val item = Task(it.name, it.status)
                    tasksList.add(item)
                }

                tasksMutableFlow.emit(tasksList)
            }

        }
    }

    fun uploadItem(task: Task) {
        viewModelScope.launch(coroutineContext) {
            model.uploadItemToDB(task)
        }
    }

}