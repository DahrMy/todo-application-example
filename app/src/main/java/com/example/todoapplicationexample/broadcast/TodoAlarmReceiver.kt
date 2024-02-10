package com.example.todoapplicationexample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todoapplicationexample.notifications.TodoNotificationHelper

class TodoAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val text = intent?.getStringExtra("key")
        context?.let {
            val notificationHelper = TodoNotificationHelper(context)
            notificationHelper.getNotification()
            notificationHelper.updateNotification(text)
        }
    }
}