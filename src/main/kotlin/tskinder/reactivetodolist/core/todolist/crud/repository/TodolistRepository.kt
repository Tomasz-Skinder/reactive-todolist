package tskinder.reactivetodolist.core.todolist.crud.repository

import reactor.core.publisher.Mono

interface TodolistRepository {

    fun save(name: String): Mono<Todolist>

    fun getById(id: Long): Mono<Todolist>
}