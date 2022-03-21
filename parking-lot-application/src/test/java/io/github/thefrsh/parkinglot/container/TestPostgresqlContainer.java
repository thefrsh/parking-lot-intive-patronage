package io.github.thefrsh.parkinglot.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestPostgresqlContainer extends PostgreSQLContainer<TestPostgresqlContainer> {

    public TestPostgresqlContainer(String dockerImageName) {

        super(dockerImageName);
        withUsername("postgres");
        withPassword("postgres");
        withReuse(true);
    }
}
