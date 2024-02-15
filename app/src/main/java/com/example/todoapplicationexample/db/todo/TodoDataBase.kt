package com.example.todoapplicationexample.db.todo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapplicationexample.db.todo.tables.tasks.TasksDao
import com.example.todoapplicationexample.db.todo.tables.tasks.TaskEntity

@Database(entities = [TaskEntity::class], version = 2, exportSchema = true)
abstract class TodoDataBase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao

}