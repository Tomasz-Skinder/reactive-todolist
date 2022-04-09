package tskinder.reactivetodolist.core.todolist.crud.repository

import java.time.Instant
import java.util.UUID

data class Item(
    val id: UUID,
    val content: String,
    val status: String,
    val creationDate: Instant,
    val modificationDate: Instant?,
    val deadline: Instant?,
    val todolistId: Long
)