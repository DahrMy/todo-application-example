package com.example.todoapplicationexample.todo.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.todoapplicationexample.Constants
import com.example.todoapplicationexample.broadcast.TodoAlarmReceiver
import com.example.todoapplicationexample.db.todo.tables.tasks.TasksDao
import com.example.todoapplicationexample.db.todo.tables.tasks.TaskEntity
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.flow.Flow

class TasksListModel(
    private val context: Context,
    private val tasksDao: TasksDao
) {

    fun getTaskEntities(): Flow<List<TaskEntity>> = tasksDao.getAllTaskEntities()
    fun getTasksNames(): Flow<List<String>> = tasksDao.getAllTasksNames()
    fun getTasksStatuses(): Flow<List<TaskStatus>> = tasksDao.getAllTasksStatuses()
    suspend fun uploadItemToDB(item: Task): Long {
        return tasksDao.insertTask(TaskEntity(
            name = item.name,
            status = item.status,
            remindTime = item.remindTime
        ))
    }

    fun setItemReminder(item: Task) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TodoAlarmReceiver::class.java)
        intent.putExtra(Constants.REMINDER_EXTRA, item)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id!!.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, item.remindTime!!, pendingIntent)

    }

}