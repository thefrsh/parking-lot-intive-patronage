package io.github.thefrsh.parkinglot.domain.sharedkernel;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class Then<T> {

    private final T candidate;
    private final boolean satisfied;

    public Otherwise then(Consumer<T> consumer) {

        if(satisfied) {
            consumer.accept(candidate);
        }
        return new Otherwise(satisfied);
    }
}
