package com.example.todoapplicationexample.db.todo.tables.tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapplicationexample.todo.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks_table")
    fun getAllTaskEntities(): Flow<List<TaskEntity>>

    @Query("SELECT id FROM tasks_table")
    fun getAllTasksId(): Flow<List<Long>>

    @Query("SELECT name FROM tasks_table")
    fun getAllTasksNames(): Flow<List<String>>

    @Query("SELECT status FROM tasks_table")
    fun getAllTasksStatuses(): Flow<List<TaskStatus>>

    @Query("SELECT remindTime FROM tasks_table")
    fun getAllTasksRemindTime(): Flow<List<Long>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

}