package tskinder.reactivetodolist.core.todolist.crud.repository

import io.r2dbc.spi.Row
import tskinder.reactivetodolist.core.todolist.crud.excpetion.ResourceNotFoundException

interface AbstractRepository {

    fun <V> get(row: Row, column: String, type: Class<V>): V? = row.get(column, type)

    fun <V> getOrThrow(row: Row, column: String, type: Class<V>): V {
        val result = row.get(column, type)
        return result ?: throw ResourceNotFoundException()
    }
}