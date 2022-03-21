package io.github.thefrsh.parkinglot.infrastructure.event;

import io.github.thefrsh.dddcqrs.domain.DomainEvent;
import io.github.thefrsh.dddcqrs.domain.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StandardEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {

        applicationEventPublisher.publishEvent(event);
    }
}
