package tskinder.reactivetodolist.core.todolist.crud.repository

import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import org.springframework.r2dbc.core.DatabaseClient
import tskinder.reactivetodolist.core.todolist.crud.excpetion.ResourceNotFoundException

interface AbstractRepository {

    fun <V> get(row: Row, column: String, type: Class<V>): V? = row.get(column, type)

    fun <V> getOrThrow(row: Row, column: String, type: Class<V>): V {
        val result = row.get(column, type)
        return result ?: throw ResourceNotFoundException()
    }

    fun <V> Statement.bindIfNotNull(index: String, value: Any?, type: Class<V>): Statement {
        return if (value != null) {
            bind(index, value)
        } else {
            bindNull(index, type)
        }
    }

    fun <V> DatabaseClient.GenericExecuteSpec.bindIfNotNull(
        index: String,
        value: Any?,
        type: Class<V>
    ): DatabaseClient.GenericExecuteSpec {
        return if (value != null) {
            bind(index, value)
        } else {
            bindNull(index, type)
        }
    }
}