package tskinder.reactivetodolist.core.todolist.crud.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.service.TodolistService
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistInputDto
import tskinder.reactivetodolist.core.todolist.crud.service.dto.TodolistOutputDto

@RestController
@RequestMapping("todolist")
class TodolistEndpoint(
    private val todolistService: TodolistService
) {

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody request: TodolistInputDto): Mono<TodolistOutputDto> {
        return todolistService.create(request)
    }

    @GetMapping("{id}")
    fun find(@PathVariable id: Long): Mono<TodolistOutputDto> {
        return todolistService.find(id)
    }
}