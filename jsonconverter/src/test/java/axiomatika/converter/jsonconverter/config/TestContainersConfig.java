package axiomatika.converter.jsonconverter.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestContainersConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("password");

    public void initialize(ConfigurableApplicationContext ctx) {
        postgresContainer.start();

        System.setProperty("SPRING_DATASOURCE_URL", postgresContainer.getJdbcUrl());
        System.setProperty("SPRING_DATASOURCE_USERNAME", postgresContainer.getUsername());
        System.setProperty("SPRING_DATASOURCE_PASSWORD", postgresContainer.getPassword());
    }
}