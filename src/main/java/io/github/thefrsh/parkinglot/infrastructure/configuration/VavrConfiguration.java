package io.github.thefrsh.parkinglot.infrastructure.configuration;

import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VavrConfiguration {

    @Bean
    public Module vavrJacksonModule() {

        return new VavrModule();
    }
}
