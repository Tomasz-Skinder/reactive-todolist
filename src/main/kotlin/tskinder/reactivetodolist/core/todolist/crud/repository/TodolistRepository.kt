package tskinder.reactivetodolist.core.todolist.crud.repository

import io.r2dbc.spi.Row
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId

interface TodolistRepository : AbstractRepository {

    fun save(name: String): Mono<TodolistId>

    fun getById(id: Long): Mono<Todolist>

    fun resultToTodolist(row: Row): Todolist {
        return Todolist(
            getOrThrow(row, "id", Number::class.java).toLong(),
            getOrThrow(row, "name", String::class.java)
        )
    }
}