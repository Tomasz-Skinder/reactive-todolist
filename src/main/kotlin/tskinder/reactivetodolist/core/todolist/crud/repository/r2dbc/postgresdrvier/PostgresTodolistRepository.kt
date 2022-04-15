package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import org.reactivestreams.Publisher
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.Todolist
import tskinder.reactivetodolist.core.todolist.crud.repository.TodolistRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.GET_TODOLIST_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.INSERT_TODOLIST

@Profile("postgres")
@Repository
class PostgresTodolistRepository(
    connectionFactory: ConnectionFactory
) : TodolistRepository {

    private val connection: Publisher<out Connection> = connectionFactory.create()

    override fun save(name: String): Mono<TodolistId> {
        return Flux.from(connection)
            .map { it.createStatement(INSERT_TODOLIST).bind("$1", name) }
            .flatMap { it.execute() }
            .flatMap { result ->
                result.map { row, _ -> getOrThrow(row, "id", Number::class.java).toLong() }
            }
            .last()
    }

    override fun getById(id: Long): Mono<Todolist> {
        return Flux.from(connection)
            .map { it.createStatement(GET_TODOLIST_BY_ID).bind("$1", id) }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> resultToTodolist(row) } }
            .last()
    }
}
