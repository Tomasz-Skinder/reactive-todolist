package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient

import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.Todolist
import tskinder.reactivetodolist.core.todolist.crud.repository.TodolistRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.GET_TODOLIST_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.INSERT_TODOLIST

@Profile("r2dbc")
@Repository
class PostgresTodolistRepository(
    private val client: DatabaseClient
) : TodolistRepository {

    override fun save(name: String): Mono<Long> {
        return client
            .sql(INSERT_TODOLIST)
            .bind("name", name)
            .map { row -> getOrThrow(row, "id", Number::class.java).toLong() }
            .one()
    }

    override fun getById(id: Long): Mono<Todolist> {
        return client
            .sql(GET_TODOLIST_BY_ID)
            .bind("id", id)
            .map { row ->  resultToTodolist(row) }
            .one()
    }
}