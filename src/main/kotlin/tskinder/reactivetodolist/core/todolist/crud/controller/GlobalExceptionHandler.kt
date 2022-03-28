package tskinder.reactivetodolist.core.todolist.crud.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import tskinder.reactivetodolist.core.todolist.crud.excpetion.ResourceNotFoundException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class, NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFound() {}
}