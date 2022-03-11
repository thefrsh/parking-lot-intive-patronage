package io.github.thefrsh.parkinglot.domain.sharedkernel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Otherwise {

    private final boolean satisfied;

    public void otherwise(Runnable runnable) {

        if (!satisfied) {
            runnable.run();
        }
    }
}
