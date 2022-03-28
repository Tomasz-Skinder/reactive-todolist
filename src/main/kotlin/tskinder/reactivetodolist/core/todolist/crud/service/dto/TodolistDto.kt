package tskinder.reactivetodolist.core.todolist.crud.service.dto

import tskinder.reactivetodolist.core.todolist.crud.repository.item.Item
import tskinder.reactivetodolist.core.todolist.crud.repository.todolist.Todolist

data class TodolistInputDto(val name: String)

data class TodolistOutputDto(
    val id: Long,
    val name: String,
    val items: List<Item>
)

fun Todolist.toOutputDtoWithoutItems() = TodolistOutputDto(id, name, emptyList())