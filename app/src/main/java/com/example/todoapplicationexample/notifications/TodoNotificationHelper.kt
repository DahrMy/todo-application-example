package com.example.todoapplicationexample.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.todoapplicationexample.MainActivity
import com.example.todoapplicationexample.R

class TodoNotificationHelper(private val context: Context) {

    companion object {
        const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "TODO_REMINDERS"
        private const val CHANNEL_NAME = "Todo reminders"
    }

    private val contentIntent by lazy {
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Remind")
            .setSound(null)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
    }

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)

    fun getNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createChannel())
        }
        return notificationBuilder.build()
    }

    fun updateNotification(text: String?) {
        text?.let {
            notificationBuilder.setContentText(it)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build()) // check second argument
    }

}