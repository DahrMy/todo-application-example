package com.example.todoapplicationexample.todo.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TasksListViewModel(
    private val model: TasksListModel
) : ViewModel() {

    private val coroutineContext = Job() + Dispatchers.IO

    private val tasksLiveData = MutableLiveData<List<Task>>()

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }

    fun getListLiveData(): LiveData<List<Task>> = tasksLiveData

    fun loadList(status: TaskStatus) {
        viewModelScope.launch(coroutineContext) {
            val result = model.getTasks().filter {
                it.status == status
            }
            tasksLiveData.postValue(result)
        }
    }

}