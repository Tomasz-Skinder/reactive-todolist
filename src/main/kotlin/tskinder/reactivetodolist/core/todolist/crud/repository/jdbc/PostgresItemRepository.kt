package tskinder.reactivetodolist.core.todolist.crud.repository.jdbc

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.CreatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.Item
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.UpdatedItem
import java.util.*

@Profile("jdbc")
@Repository
class PostgresItemRepository : ItemRepository {

    override fun save(item: CreatedItem): Mono<UUID> {
        TODO("Not yet implemented")
    }

    override fun getById(todolistId: Long, id: UUID): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        TODO("Not yet implemented")
    }

    override fun update(id: UUID, item: UpdatedItem): Mono<UUID> {
        TODO("Not yet implemented")
    }

    override fun delete(todolistId: Long, id: UUID): Mono<Int> {
        TODO("Not yet implemented")
    }
}