package com.example.todoapplicationexample.todo

data class Task(
    var name: String,
    var status: TaskStatus
)

object TaskUtils { // TODO: question(Can I make it protected?)
    fun generateSimpleList(): List<Task> {

        // Generated AI list
        return listOf(
            Task("Buy groceries", TaskStatus.DONE),
            Task("Clean the house", TaskStatus.IN_PROGRESS),
            Task("Call mom", TaskStatus.DONE),
            Task("Finish homework", TaskStatus.IN_PROGRESS),
            Task("Pay bills", TaskStatus.DONE),
            Task("Book flight tickets", TaskStatus.IN_PROGRESS),
            Task("Cancel gym membership", TaskStatus.DELETED),
            Task("Write a blog post", TaskStatus.IN_PROGRESS),
            Task("Watch a movie", TaskStatus.DONE),
            Task("Update resume", TaskStatus.IN_PROGRESS),
            Task("Send birthday card", TaskStatus.DONE),
            Task("Donate clothes", TaskStatus.IN_PROGRESS),
            Task("Schedule dentist appointment", TaskStatus.DELETED),
            Task("Learn a new skill", TaskStatus.IN_PROGRESS),
            Task("Read a book", TaskStatus.DONE)
        )
    }

}