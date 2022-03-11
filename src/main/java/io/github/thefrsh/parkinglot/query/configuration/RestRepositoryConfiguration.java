package io.github.thefrsh.parkinglot.query.configuration;

import io.github.thefrsh.parkinglot.query.parkingspot.ParkingSpotProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
class RestRepositoryConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        config.setExposeRepositoryMethodsByDefault(false);
        config.exposeIdsFor(ParkingSpotProjection.class);
        config.setBasePath("/api");
    }
}
