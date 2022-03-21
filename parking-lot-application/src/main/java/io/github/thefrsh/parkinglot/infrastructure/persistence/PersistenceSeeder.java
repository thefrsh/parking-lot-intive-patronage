package io.github.thefrsh.parkinglot.infrastructure.persistence;

import io.github.thefrsh.parkinglot.infrastructure.Profiles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Profile(value = Profiles.DEVELOPMENT)
class PersistenceSeeder {

    private static final String SEED_SCRIPT = "db/clear-seed.sql";

    private final DataSource dataSource;

    @EventListener(value = ApplicationReadyEvent.class)
    public void loadDataOnStartup() {

        var populator = new ResourceDatabasePopulator(
                false, false, StandardCharsets.UTF_8.name(), new ClassPathResource(SEED_SCRIPT)
        );
        populator.execute(dataSource);
    }
}
