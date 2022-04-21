package tskinder.reactivetodolist.core.todolist.crud.repository.jdbc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Profile("jdbc")
@Configuration
class ConfigureConnection {

    @Profile("jdbc")
    @Bean
    fun datasource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = "jdbc:postgresql://localhost:5432/postgres"
        dataSource.username = "postgres"
        dataSource.password = "postgres"
        return dataSource
    }
}