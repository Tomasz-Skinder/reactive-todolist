package tskinder.reactivetodolist.core.todolist.crud.repository

import io.r2dbc.spi.Row
import reactor.core.publisher.Mono

interface TodolistRepository : AbstractRepository {

    fun save(name: String): Mono<Long>

    fun getById(id: Long): Mono<Todolist>

    fun resultToTodolist(row: Row): Todolist {
        return Todolist(
            getOrThrow(row, "id", Number::class.java).toLong(),
            getOrThrow(row, "name", String::class.java)
        )
    }
}