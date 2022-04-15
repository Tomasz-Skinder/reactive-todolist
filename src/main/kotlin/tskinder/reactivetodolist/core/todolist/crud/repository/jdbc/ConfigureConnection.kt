package tskinder.reactivetodolist.core.todolist.crud.repository.jdbc

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource


@Profile("jdbc")
@Configuration
class ConfigureConnection {

    @Profile("jdbc")
    @Bean
    fun datasource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("jdbc:postgresql://localhost:5432/postgres")
            .username("postgres")
            .password("postgres")
            .build()
    }

    @Profile("jdbc")
    @Bean
    fun namedParameterJdbcTemplate(): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(datasource())
    }
}