package tskinder.reactivetodolist.core.todolist.crud.repository.todolist

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.Row
import org.reactivestreams.Publisher
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.AbstractRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.GET_TODOLIST_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.INSERT_TODOLIST

@Repository
class R2dbcTodolistRepository(
    connectionFactory: ConnectionFactory
) : TodolistRepository, AbstractRepository {

    private val connection: Publisher<out Connection> = connectionFactory.create()

    override fun save(name: String): Mono<Todolist> {
        return Flux.from(connection)
            .map { it.createStatement(INSERT_TODOLIST).bind("$1", name) }
            .flatMap { it.execute() }
            .flatMap { result -> result.map { row, _ -> resultToTodolist(row) } }
            .last()
    }

    override fun getById(id: Long): Mono<Todolist> {
        return Flux.from(connection)
            .map { it.createStatement(GET_TODOLIST_BY_ID).bind("$1", id) }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> resultToTodolist(row) } }
            .last()
    }

    private fun resultToTodolist(row: Row): Todolist {
        return Todolist(
            getOrThrow(row, "id", Number::class.java).toLong(),
            getOrThrow(row, "name", String::class.java)
        )
    }
}
