package com.example.todoapplicationexample.todo.lists

import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskUtils
import io.reactivex.rxjava3.core.Observable

class TasksListModel {

    fun getTasks(): Observable<List<Task>> {
        val list = TaskUtils.generateSimpleList()
        return Observable.just(list)
    }

}