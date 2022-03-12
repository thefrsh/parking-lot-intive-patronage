package io.github.thefrsh.parkinglot.query;

import io.github.thefrsh.parkinglot.query.parkingspot.ParkingSpotProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
class RestRepositoryConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        config.setDefaultMediaType(MediaTypes.HAL_JSON);
        config.exposeIdsFor(ParkingSpotProjection.class);
        config.setExposeRepositoryMethodsByDefault(false);
        config.setBasePath("/api");
    }
}
