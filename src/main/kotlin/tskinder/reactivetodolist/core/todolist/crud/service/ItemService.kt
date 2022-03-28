package tskinder.reactivetodolist.core.todolist.crud.service

import ItemInputDto
import ItemOutputDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import toCreatedItem
import toItemOutputDto
import tskinder.reactivetodolist.core.todolist.crud.repository.item.ItemRepository

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
}