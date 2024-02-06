package com.example.todoapplicationexample.todo.viewmodel

import com.example.todoapplicationexample.db.todo.tables.tasks.TasksDao
import com.example.todoapplicationexample.db.todo.tables.tasks.TaskEntity
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.flow.Flow

class TasksListModel(private val tasksDao: TasksDao) {

    fun getTaskEntities(): Flow<List<TaskEntity>> = tasksDao.getAllTaskEntities()
    fun getTasksNames(): Flow<List<String>> = tasksDao.getAllTasksNames()
    fun getTasksStatuses(): Flow<List<TaskStatus>> = tasksDao.getAllTasksStatuses()
    suspend fun uploadItemToDB(item: Task) {
        tasksDao.insertTask(TaskEntity(
            name = item.name,
            status = item.status
        ))
    }

}