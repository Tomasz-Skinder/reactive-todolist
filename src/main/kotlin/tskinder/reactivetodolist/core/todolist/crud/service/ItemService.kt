package tskinder.reactivetodolist.core.todolist.crud.service

import ItemInputDto
import ItemOutputDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import toCreatedItem
import toItemOutputDto
import toUpdatedItem
import tskinder.reactivetodolist.core.todolist.crud.config.ItemId
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {

    fun create(todolistId: TodolistId, item: ItemInputDto): Mono<ItemId> {
        return itemRepository.save(item.toCreatedItem(todolistId))
    }

    fun findAllByTodolistId(todolistId: Long): Flux<ItemOutputDto> {
        return itemRepository.getAllByTodolistId(todolistId)
            .map { it.toItemOutputDto() }
    }

    fun find(todolistId: Long, id: ItemId): Mono<ItemOutputDto> {
        return itemRepository.getById(todolistId, id)
            .map { it.toItemOutputDto() }
    }

    fun update(id: ItemId, item: ItemInputDto): Mono<ItemId> {
        return itemRepository.update(id, item.toUpdatedItem())
    }

    fun remove(todolistId: Long, id: ItemId): Mono<Int> {
        return itemRepository.delete(todolistId, id)
    }
}