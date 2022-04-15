package tskinder.reactivetodolist.core.todolist.crud.repository

import io.r2dbc.spi.Row
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.UUID

interface ItemRepository : AbstractRepository {

    fun save(item: CreatedItem): Mono<UUID>

    fun getById(todolistId: Long, id: UUID): Mono<Item>

    fun getAllByTodolistId(todolistId: Long): Flux<Item>

    fun update(id: UUID, item: UpdatedItem): Mono<UUID>

    fun delete(todolistId: Long, id: UUID): Mono<Int>

    fun resultToItem(row: Row): Item {
        return Item(
            id = getOrThrow(row, "id", UUID::class.java),
            content = getOrThrow(row, "content", String::class.java),
            status = getOrThrow(row, "status", String::class.java),
            creationDate = getOrThrow(row, "creation_date", Instant::class.java),
            modificationDate = get(row, "modification_date", Instant::class.java),
            deadline = get(row, "deadline", Instant::class.java),
            todolistId = getOrThrow(row, "todolist_id", Number::class.java).toLong()
        )
    }
}

data class CreatedItem(
    val content: String,
    val status: String,
    val creationDate: Instant,
    val deadline: Instant?,
    val todolistId: Long
)

data class UpdatedItem(
    val content: String,
    val modificationDate: Instant,
    val deadline: Instant?
)