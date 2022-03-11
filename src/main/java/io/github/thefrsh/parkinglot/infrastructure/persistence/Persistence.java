package io.github.thefrsh.parkinglot.infrastructure.persistence;

public interface Persistence<T, I extends Number> {

    T loadById(I id) throws ResourceNotFoundException;
}
