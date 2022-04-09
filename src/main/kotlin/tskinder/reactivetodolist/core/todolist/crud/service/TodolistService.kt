package tskinder.reactivetodolist.core.todolist.crud.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.repository.TodolistRepository
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistInputDto
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistOutputDto
import tskinder.reactivetodolist.core.todolist.crud.service.dto.toOutputDtoWithoutItems

@Service
class TodolistService(
    private val todolistRepository: TodolistRepository,
) {
    fun create(todolistInputDto: TodolistInputDto): Mono<TodolistOutputDto> {
        return todolistRepository
            .save(todolistInputDto.name)
            .map { it.toOutputDtoWithoutItems() }
    }

    fun find(id: Long): Mono<TodolistOutputDto> {
        return todolistRepository
            .getById(id)
            .map { it.toOutputDtoWithoutItems() }
    }
}