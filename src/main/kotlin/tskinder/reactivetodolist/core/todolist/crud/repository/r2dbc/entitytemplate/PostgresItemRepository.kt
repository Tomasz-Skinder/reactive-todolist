package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.entitytemplate

import org.springframework.context.annotation.Profile
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.CreatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.Item
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.UpdatedItem
import java.util.UUID

@Profile("r2dbc")
@Repository
@Transactional
class PostgresItemRepository(
    private val template: R2dbcEntityTemplate
) : ItemRepository {

    override fun save(item: CreatedItem): Mono<Item> {
        TODO("Not yet implemented")
    }

    override fun getById(todolistId: Long, id: String): Mono<Item> {
        return template.selectOne(
            Query.query(where("id").`is`(UUID.fromString(id))),
            Item::class.java
        )
    }

    override fun getAllByTodolistId(todolistId: Long): Flux<Item> {
        return template.select(Item::class.java)
            .matching(Query.query(where("todolist_id").`is`(todolistId)))
            .all()
    }

    override fun update(id: String, item: UpdatedItem) {
        TODO("Not yet implemented")
    }

    override fun delete(todolistId: Long, id: String): Mono<Int> {
        return template.delete(
            Query.query(where("id").`is`(id)),
            Item::class.java
        )
    }
}