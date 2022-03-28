package tskinder.reactivetodolist.core.todolist.crud.repository.item

import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import org.reactivestreams.Publisher
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.AbstractRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.GET_ITEMS_BY_TODOLIST_ID
import tskinder.reactivetodolist.core.todolist.crud.repository.INSERT_ITEM
import java.time.Instant

@Profile("noSpring")
@Repository
class R2dbcItemRepository(
    connectionFactory: ConnectionFactory
) : ItemRepository {

    private val connection: Publisher<out Connection> = connectionFactory.create()

    override fun save(item: CreatedItem): Mono<Item> {
        return Flux.from(connection)
            .map {
                it.createStatement(INSERT_ITEM)
                    .bind("$1", item.content)
                    .bind("$2", item.status)
                    .bind("$3", item.creationDate)
                    .bindIfNotNull("$4", item.deadline, Instant::class.java)
                    .bind("$5", item.todolistId)
            }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> resultToItem(row) } }
            .last()
    }

    override fun getById(todolistId: Long, id: String): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        return Flux.from(connection)
            .map { it.createStatement(GET_ITEMS_BY_TODOLIST_ID).bind("$1", todolistId) }
            .flatMap { it.execute() }
            .flatMap { it.map { row, _ -> resultToItem(row) } }
    }

    override fun update(item: UpdatedItem): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun delete(todolistId: Long, id: String): Mono<Int> {
        TODO("Not yet implemented")
    }
}

fun <V> Statement.bindIfNotNull(index: String, value: Any?, type: Class<V>): Statement {
    return if (value != null) {
        bind(index, value)
    } else {
        bindNull(index, type)
    }
}