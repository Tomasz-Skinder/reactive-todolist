package tskinder.reactivetodolist.core.todolist.crud.service

import ItemInputDto
import ItemOutputDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import toCreatedItem
import toItemOutputDto
import toUpdatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {

    fun create(todolistId: Long, item: ItemInputDto): Mono<ItemOutputDto> {
        val createdItem = item.toCreatedItem(todolistId)
        return itemRepository.save(createdItem)
            .map { it.toItemOutputDto() }
    }

    fun findAllByTodolistId(todolistId: Long): Flux<ItemOutputDto> {
        return itemRepository.getAllByTodolistId(todolistId)
            .map { it.toItemOutputDto() }
    }

    fun find(todolistId: Long, id: String): Mono<ItemOutputDto> {
        return itemRepository.getById(todolistId, id)
            .map { it.toItemOutputDto() }
    }

    fun update(id: String, item: ItemInputDto) {
        itemRepository.update(id, item.toUpdatedItem())
    }

    fun remove(todolistId: Long, id: String): Mono<Int> {
        return itemRepository.delete(todolistId, id)
    }
}