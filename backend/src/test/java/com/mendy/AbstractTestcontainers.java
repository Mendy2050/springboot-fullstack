package com.mendy;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mendy
 * @create 2023-08-10 20:06
 */

@Testcontainers
public abstract class AbstractTestcontainers {
    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().
                dataSource(
                        posrgreSQLContainer.getJdbcUrl(),
                        posrgreSQLContainer.getUsername(),
                        posrgreSQLContainer.getPassword()
                )
                .load();
        flyway.migrate();
        System.out.println();
    }


    @Container
    protected  static  final PostgreSQLContainer<?> posrgreSQLContainer
            = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("amigoscode-dao-unit-test")
            .withUsername("amigoscode")
            .withPassword("password");


    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",posrgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",posrgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",posrgreSQLContainer::getPassword);
    }


    private static DataSource getDataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create()
                .driverClassName(posrgreSQLContainer.getDriverClassName())
                .url(posrgreSQLContainer.getJdbcUrl())
                .username(posrgreSQLContainer.getUsername())
                .password(posrgreSQLContainer.getPassword());

        return builder.build();
    }


    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker FAKER = new Faker();
}
