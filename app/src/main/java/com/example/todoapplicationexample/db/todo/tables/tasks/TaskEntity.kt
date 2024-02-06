package com.example.todoapplicationexample.db.todo.tables.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapplicationexample.todo.TaskStatus

@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val status: TaskStatus
)