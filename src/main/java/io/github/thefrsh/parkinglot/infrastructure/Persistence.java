package io.github.thefrsh.parkinglot.infrastructure;

public interface Persistence<T, I extends Number> {

    T loadById(I id);
}
