package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient

import io.r2dbc.spi.Statement
import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.ExecuteFunction
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.CreatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.Item
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.UpdatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.DELETE_ITEM
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.GET_ITEMS_BY_TODOLIST_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.GET_ITEM_BY_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.INSERT_ITEM
import tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient.DatabaseClientQuery.UPDATE_ITEM_CONTENT
import java.time.Instant
import java.util.UUID

@Profile("r2dbc")
@Repository
class PostgresItemRepository(
    private val client: DatabaseClient
) : ItemRepository {

    override fun save(item: CreatedItem): Mono<UUID> {
        return client.sql(INSERT_ITEM)
            .filter { s: Statement, next: ExecuteFunction ->
                next.execute(s.returnGeneratedValues("id"))
            }
            .bind("content", item.content)
            .bind("status", item.status)
            .bind("creationDate", item.creationDate)
            .bindIfNotNull("deadline", item.deadline, Instant::class.java)
            .bind("todolistId", item.todolistId)
            .fetch()
            .one()
            .map { row -> row["id"] as UUID }
    }

    override fun getById(todolistId: Long, id: UUID): Mono<Item> {
        return client
            .sql(GET_ITEM_BY_ID)
            .bind("todolistId", todolistId)
            .bind("id", id)
            .map { row -> resultToItem(row) }
            .one()
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        return client
            .sql(GET_ITEMS_BY_TODOLIST_ID)
            .bind("todolistId", todolistId)
            .filter { statement -> statement.fetchSize(2) }
            .map { row -> resultToItem(row) }
            .all()
    }

    override fun update(id: UUID, item: UpdatedItem): Mono<UUID> {
        return client
            .sql(UPDATE_ITEM_CONTENT)
            .bind("content", item.content)
            .bind("modificationDate", item.modificationDate)
            .bindIfNotNull("deadline", item.deadline, Instant::class.java)
            .bind("id", id)
            .map { row -> row.get("id", UUID::class.java) }
            .one()
    }

    override fun delete(todolistId: Long, id: UUID): Mono<Int> {
        return client
            .sql(DELETE_ITEM)
            .bind("todolistId", todolistId)
            .bind("id", id)
            .fetch()
            .rowsUpdated()
    }
}