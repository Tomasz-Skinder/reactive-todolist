package tskinder.reactivetodolist.core.todolist.crud.repository.item

import java.time.Instant

data class Item(
    val id: String,
    val content: String,
    val status: String,
    val creationDate: Instant,
    val modificationDate: Instant?,
    val deadline: Instant?,
    val todolistId: Long
)