import tskinder.reactivetodolist.core.todolist.crud.repository.item.CreatedItem
import tskinder.reactivetodolist.core.todolist.crud.repository.item.Item
import java.time.Instant

data class ItemInputDto(
    val content: String,
    val deadline: Instant?
)

data class ItemOutputDto(
    val id: String,
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

fun ItemInputDto.toCreatedItem(todolistId: Long): CreatedItem {
    return CreatedItem(
        content = content,
        status = ItemStatus.PENDING.name,
        creationDate = Instant.now(),
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

