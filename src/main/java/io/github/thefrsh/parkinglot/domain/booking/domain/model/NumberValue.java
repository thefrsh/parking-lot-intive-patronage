package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
@SuppressWarnings(value = "unused")
class NumberValue implements Serializable {

    @Min(1)
    @Column(nullable = false)
    private Integer number;

    protected NumberValue() {

    }

    private NumberValue(Integer value) {

        this.number = value;
    }

    public NumberValue from(Integer value) {

        return new NumberValue(value);
    }
}
