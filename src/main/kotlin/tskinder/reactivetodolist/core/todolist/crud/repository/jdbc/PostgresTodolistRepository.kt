package tskinder.reactivetodolist.core.todolist.crud.repository.jdbc

import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import tskinder.reactivetodolist.core.todolist.crud.config.TodolistId
import tskinder.reactivetodolist.core.todolist.crud.repository.Todolist
import tskinder.reactivetodolist.core.todolist.crud.repository.TodolistRepository
import java.sql.ResultSet
import javax.sql.DataSource

@Profile("jdbc")
@Repository
class PostgresTodolistRepository(
    private val dataSource: DataSource
) : TodolistRepository {

    val jdbcTemplate = NamedParameterJdbcTemplate(dataSource)

    override fun save(name: String): Mono<TodolistId> {
        val sql = "INSERT INTO todolist (name) VALUES (:name) RETURNING id"
        val namedParams = mapOf("name" to name)
        val generatedId = jdbcTemplate.queryForObject(sql, namedParams, Long::class.java)!!
        return Mono.just(generatedId)
    }

    override fun getById(id: Long): Mono<Todolist> {
        val sql = "SELECT id, name FROM todolist WHERE id = :id"
        val namedParams = mapOf("id" to id)
        val todolist = jdbcTemplate.queryForObject(sql, namedParams, TodolistRowMapper())!!
        return Mono.just(todolist)
    }
}

class TodolistRowMapper : RowMapper<Todolist> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Todolist {
        return Todolist(
            rs.getLong("id"),
            rs.getString("name")
        )
    }
}
