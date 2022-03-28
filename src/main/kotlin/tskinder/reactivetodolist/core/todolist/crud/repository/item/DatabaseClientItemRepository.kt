package tskinder.reactivetodolist.core.todolist.crud.repository.item

import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.DBC_INSERT_ITEM
import java.time.Instant

@Profile("spring")
@Repository
class DatabaseClientItemRepository(
    private val databaseClient: DatabaseClient
): ItemRepository {

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

    override fun getById(id: String): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        TODO("Not yet implemented")
    }

    override fun update(item: UpdatedItem): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}

fun <V> DatabaseClient.GenericExecuteSpec.bindIfNotNull(index: String, value: Any?, type: Class<V>): DatabaseClient.GenericExecuteSpec {
    return if (value != null) {
        bind(index, value)
    } else {
        bindNull(index, type)
    }
}