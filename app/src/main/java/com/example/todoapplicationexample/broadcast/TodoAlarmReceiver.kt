package com.example.todoapplicationexample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.todoapplicationexample.Constants
import com.example.todoapplicationexample.notifications.TodoNotificationHelper
import com.example.todoapplicationexample.todo.Task

class TodoAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getSerializableExtra(Constants.REMINDER_EXTRA, Task::class.java) as Task
        } else {
            @Suppress("DEPRECATION")
            intent?.getSerializableExtra(Constants.REMINDER_EXTRA) as Task
        }

        context?.let {
            val notificationHelper = TodoNotificationHelper(context)
            notificationHelper.getNotification()
            notificationHelper.updateNotification(task.name)
        }
    }
}