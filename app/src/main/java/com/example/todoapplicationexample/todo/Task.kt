package com.example.todoapplicationexample.todo

import java.io.Serializable

data class Task(
    var id: Long?,
    var name: String,
    var status: TaskStatus,
    var remindTime: Long?
) : Serializable { // TODO: Change to Parcelable

    companion object {
        fun generateSimpleList(): List<Task> {

            // Generated AI list
            return listOf(
                Task(null, "Buy groceries", TaskStatus.DONE, null),
                Task(null, "Clean the house", TaskStatus.IN_PROGRESS, null),
                Task(null, "Call mom", TaskStatus.DONE, null),
                Task(null, "Finish homework", TaskStatus.IN_PROGRESS, null),
                Task(null, "Pay bills", TaskStatus.DONE, null),
                Task(null, "Book flight tickets", TaskStatus.IN_PROGRESS, null),
                Task(null, "Cancel gym membership", TaskStatus.DELETED, null),
                Task(null, "Write a blog post", TaskStatus.IN_PROGRESS, null),
                Task(null, "Watch a movie", TaskStatus.DONE, null),
                Task(null, "Update resume", TaskStatus.IN_PROGRESS, null),
                Task(null, "Send birthday card", TaskStatus.DONE, null),
                Task(null, "Donate clothes", TaskStatus.IN_PROGRESS, null),
                Task(null, "Schedule dentist appointment", TaskStatus.DELETED, null),
                Task(null, "Learn a new skill", TaskStatus.IN_PROGRESS, null),
                Task(null, "Read a book", TaskStatus.DONE, null)
            )
        }

    }

}