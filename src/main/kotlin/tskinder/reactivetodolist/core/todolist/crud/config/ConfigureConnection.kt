package tskinder.reactivetodolist.core.todolist.crud.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient

@Configuration
class ConfigureConnection {

    val connectionFactory: PostgresqlConnectionFactory = PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
        .host("localhost")
        .port(5432)
        .database("postgres")
        .username("postgres")
        .password("postgres")
        .build())

    @Bean
    fun getConnectionFactory(): ConnectionFactory {
        return connectionFactory
    }

    @Bean
    fun getDatabaseClient(): DatabaseClient {
        return DatabaseClient.builder()
            .connectionFactory(connectionFactory)
            .namedParameters(true)
            .build()
    }
}