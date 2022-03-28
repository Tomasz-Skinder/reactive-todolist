package tskinder.reactivetodolist.core.todolist.crud.controller

import ItemInputDto
import ItemOutputDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.service.ItemService

@RestController
@RequestMapping("todolist/{todolistId}/item")
class ItemEndpoint(
    private val itemService: ItemService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@PathVariable todolistId: Long, @RequestBody request: ItemInputDto): Mono<ItemOutputDto> {
        return itemService.create(todolistId, request)
    }

    @GetMapping
    fun findAllByTodolistId(@PathVariable todolistId: Long): Flux<ItemOutputDto> {
        return itemService.findAllByTodolistId(todolistId)
    }
}