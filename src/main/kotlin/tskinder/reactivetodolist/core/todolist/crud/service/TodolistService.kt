package tskinder.reactivetodolist.core.todolist.crud.service

import ItemInputDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import toCreatedItem
import tskinder.reactivetodolist.core.todolist.crud.config.ItemId
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.ItemRepository
import tskinder.reactivetodolist.core.todolist.crud.repository.TodolistRepository
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistInputDto
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistOutputDto
import tskinder.reactivetodolist.core.todolist.crud.service.dto.toOutputDtoWithoutItems

@Service
class TodolistService(
    private val todolistRepository: TodolistRepository,
    private val itemRepository: ItemRepository
) {
    fun create(todolist: TodolistInputDto): Mono<TodolistId> {
        return todolistRepository.save(todolist.name)
    }

    fun find(id: TodolistId): Mono<TodolistOutputDto> {
        return todolistRepository
            .getById(id)
            .map { it.toOutputDtoWithoutItems() }
    }

    @Transactional
    fun createWithItem(todolist: TodolistInputDto, items: ItemInputDto): Mono<ItemId> {
        return todolistRepository.save(todolist.name).flatMap {
            itemRepository.save(items.toCreatedItem(it))
        }
    }
}