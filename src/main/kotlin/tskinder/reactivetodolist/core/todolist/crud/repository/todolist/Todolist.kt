package tskinder.reactivetodolist.core.todolist.crud.repository.todolist

data class Todolist(
    val id: Long,
    val name: String
)

data class TodolistWithItems(
    val id: Long,
    val name: String,
    val items: MutableList<String>
)