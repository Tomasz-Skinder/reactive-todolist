package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.*
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.DELETE_ITEM_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.GET_ITEMS_BY_TODOLIST_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.GET_ITEM_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.INSERT_ITEM
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier.PostgresQuery.UPDATE_ITEM_CONTENT
import java.time.Instant
import java.util.UUID

@Profile("postgres")
@Repository
class PostgresItemRepository(
    private val connectionFactory: ConnectionFactory
) : ItemRepository {

    private val connection: Flux<Connection> = Flux.from(connectionFactory.create())

    override fun save(item: CreatedItem): Mono<UUID> {
        return connection
            .map {
                it.createStatement(INSERT_ITEM)
                    .bind("$1", item.content)
                    .bind("$2", item.status)
                    .bind("$3", item.creationDate)
                    .bindIfNotNull("$4", item.deadline, Instant::class.java)
                    .bind("$5", item.todolistId)
            }
            .flatMap { it.execute() }
            .flatMap {
                it.map { row, _ -> getOrThrow(row, "id", UUID::class.java) }
            }
            .last()
    }

    override fun getById(todolistId: Long, id: UUID): Mono<Item> {
        return connection
            .map {
                it.createStatement(GET_ITEM_BY_ID)
                    .bind("$1", id)
                    .bind("$2", todolistId)
            }
            .flatMap { it.execute() }
            .flatMap {
                it.map { row, _ -> resultToItem(row) }
            }
            .last()
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        return connection
            .map {
                it.createStatement(GET_ITEMS_BY_TODOLIST_ID)
                    .bind("$1", todolistId)
            }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> resultToItem(row) } }
    }

    override fun update(id: UUID, item: UpdatedItem): Mono<UUID> {
        return connection
            .map {
                it.beginTransaction()
                it.setAutoCommit(true)
                it
            }
            .map {
                it.createStatement(UPDATE_ITEM_CONTENT)
                    .bind("$1", item.content)
                    .bind("$2", item.modificationDate)
                    .bindIfNotNull("$3", item.deadline, Instant::class.java)
                    .bind("$4", id)
            }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> getOrThrow(row, "id", UUID::class.java) } }
            .last()
    }

    override fun delete(todolistId: Long, id: UUID): Mono<Int> {
        return connection
            .map {
                it.createStatement(DELETE_ITEM_BY_ID)
                    .bind("$1", id)
                    .bind("$2", todolistId)
            }
            .flatMap { it.execute() }
            .flatMap { it.rowsUpdated }
            .last()
    }
}