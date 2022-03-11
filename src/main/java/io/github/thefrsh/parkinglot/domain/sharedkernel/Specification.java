package io.github.thefrsh.parkinglot.domain.sharedkernel;

public interface Specification<T> {

    boolean isSatisfiedBy(T candidate);

    default Then<T> ifSatisifed(T candidate) {

        return new Then<>(candidate, isSatisfiedBy(candidate));
    }
}
