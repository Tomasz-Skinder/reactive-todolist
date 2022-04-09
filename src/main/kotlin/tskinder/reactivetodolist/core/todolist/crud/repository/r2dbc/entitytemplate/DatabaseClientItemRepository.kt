package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.entitytemplate

import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.*
import java.time.Instant
import java.util.UUID

@Profile("dbclient")
@Repository
class DatabaseClientItemRepository(
    private val databaseClient: DatabaseClient
) : ItemRepository {

    override fun save(item: CreatedItem): Mono<Item> {
        return databaseClient
            .sql(DBC_INSERT_ITEM)
            .bind("content", item.content)
            .bind("status", item.status)
            .bind("creationDate", item.creationDate)
            .bindIfNotNull("deadline", item.deadline, Instant::class.java)
            .bind("todolistId", item.todolistId)
            .map { row, _ -> resultToItem(row) }
            .first()
    }

    override fun getById(todolistId: Long, id: String): Mono<Item> {
        return databaseClient
            .sql(DBC_GET_ITEM_BY_ID)
            .bind("todolistId", todolistId)
            .bind("id", UUID.fromString(id))
            .map { row, _ -> resultToItem(row) }
            .first()
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        return databaseClient
            .sql(DBC_GET_TODOLIST_BY_ID)
            .bind("todolistId", todolistId)
            .map { row, _ -> resultToItem(row) }
            .all()
    }

    override fun update(id: String, item: UpdatedItem) {
        TODO("Not yet implemented")
    }

    override fun delete(todolistId: Long, id: String): Mono<Int> {
        return databaseClient
            .sql(DBC_DELETE_ITEM)
            .bind("todolistId", todolistId)
            .bind("id", UUID.fromString(id))
            .fetch()
            .rowsUpdated()
    }
}

fun <V> DatabaseClient.GenericExecuteSpec.bindIfNotNull(
    index: String,
    value: Any?,
    type: Class<V>
): DatabaseClient.GenericExecuteSpec {
    return if (value != null) {
        bind(index, value)
    } else {
        bindNull(index, type)
    }
}