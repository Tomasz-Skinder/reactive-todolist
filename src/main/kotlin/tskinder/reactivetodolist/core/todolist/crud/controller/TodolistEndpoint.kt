package tskinder.reactivetodolist.core.todolist.crud.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.config.ItemId
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.service.TodolistService
import tskinder.reactivetodolist.core.todolist.crud.service.dto.*

@RestController
@RequestMapping("todolist")
class TodolistEndpoint(
    private val todolistService: TodolistService
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody request: TodolistInputDto): Mono<TodolistId> {
        return todolistService.create(request)
    }

    @PostMapping("item")
    @ResponseStatus(CREATED)
    fun createWithItem(@RequestBody request: TodolistWithItemInputDto): Mono<ItemId> {
        return todolistService.createWithItem(request.toTodolistInputDto(), request.toItemInputDto())
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: TodolistId): Mono<TodolistOutputDto> {
        return todolistService.find(id)
    }
}