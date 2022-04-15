package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.r2dbc.core.DatabaseClient

@Profile("r2dbc")
@Configuration
class ConfigureConnection {

    @Bean
    @Profile("r2dbc")
    fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .database("postgres")
                .username("postgres")
                .password("postgres")
                .build()
        )
    }

    @Bean
    @Profile("r2dbc")
    fun databaseClient(): DatabaseClient {
        return DatabaseClient.create(connectionFactory())
    }
}