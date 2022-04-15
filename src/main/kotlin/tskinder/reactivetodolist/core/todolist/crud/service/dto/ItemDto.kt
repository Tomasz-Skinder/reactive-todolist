import tskinder.reactivetodolist.core.todolist.crud.config.ItemId
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.CreatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.Item
import tskinder.reactivetodolist.core.todolist.crud.repository.UpdatedItem
import java.time.Instant
import java.time.Instant.now

data class ItemInputDto(
    val content: String,
    val deadline: Instant?
)

data class ItemOutputDto(
    val id: ItemId,
    val content: String,
    val timeline: Timeline,
    val status: ItemStatus
)

data class Timeline(
    val creationDate: Instant,
    val modificationDate: Instant?,
    val deadline: Instant?
)

enum class ItemStatus {
    PENDING, DONE, EXPIRED, CANCELED
}

fun ItemInputDto.toCreatedItem(todolistId: TodolistId): CreatedItem {
    return CreatedItem(
        content = content,
        status = ItemStatus.PENDING.name,
        creationDate = now(),
        deadline = deadline,
        todolistId = todolistId
    )
}

fun Item.toItemOutputDto(): ItemOutputDto {
    return ItemOutputDto(
        id = id,
        content = content,
        timeline = Timeline(creationDate, modificationDate, deadline),
        status = ItemStatus.valueOf(status)
    )
}

fun ItemInputDto.toUpdatedItem(): UpdatedItem {
    return UpdatedItem(
        content = content,
        modificationDate = now(),
        deadline = deadline
    )
}

