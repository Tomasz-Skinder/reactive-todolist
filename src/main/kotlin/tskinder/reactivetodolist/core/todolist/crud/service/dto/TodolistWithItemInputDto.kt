package tskinder.reactivetodolist.core.todolist.crud.service.dto

import ItemInputDto
import java.time.Instant


data class TodolistWithItemInputDto(
    val name: String,
    val content: String,
    val deadline: Instant?
)

fun TodolistWithItemInputDto.toTodolistInputDto(): TodolistInputDto {
    return TodolistInputDto(name)
}

fun TodolistWithItemInputDto.toItemInputDto(): ItemInputDto {
    return ItemInputDto(content, deadline)
}