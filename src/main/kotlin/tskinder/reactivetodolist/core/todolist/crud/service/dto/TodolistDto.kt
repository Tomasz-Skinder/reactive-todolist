package tskinder.reactivetodolist.core.todolist.crud.service.dto

import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.Todolist

data class TodolistInputDto(val name: String)

data class TodolistOutputDto(
    val id: TodolistId,
    val name: String
)

fun Todolist.toOutputDtoWithoutItems() = TodolistOutputDto(id, name)