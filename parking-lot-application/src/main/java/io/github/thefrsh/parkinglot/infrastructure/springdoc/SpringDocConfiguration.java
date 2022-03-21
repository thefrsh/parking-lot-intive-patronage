package io.github.thefrsh.parkinglot.infrastructure.springdoc;

import io.github.thefrsh.parkinglot.infrastructure.Profiles;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = Profiles.DEVELOPMENT)
class SpringDocConfiguration {

    private static class Domain {

        public static final String GROUP_NAME = "Domain";
        public static final String PACKAGE = "io.github.thefrsh.parkinglot.domain";
    }

    private static class Query {

        public static final String GROUP_NAME = "Query";
        public static final String PACKAGE = "io.github.thefrsh.parkinglot.query";
    }

    @Value("${project.version}")
    private String projectVersion;

    @Bean
    public GroupedOpenApi domainGroupedOpenApi() {

        return GroupedOpenApi.builder()
                .group(Domain.GROUP_NAME)
                .packagesToScan(Domain.PACKAGE)
                .build();
    }

    @Bean
    public GroupedOpenApi queryGroupedOpenApi() {

        return GroupedOpenApi.builder()
                .group(Query.GROUP_NAME)
                .packagesToScan(Query.PACKAGE)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info().title("Parking Lot")
                        .description("""
                                First recruitment task for the first stage of intive Patronage 2022.
                                Simple Java and Spring Boot based system for parking spots booking at the parking lot.
                                """)
                        .version(projectVersion))
                .externalDocs(new ExternalDocumentation()
                        .description("README.md")
                        .url("https://github.com/thefrsh/parking-lot-intive-patronage#readme"));
    }
}
