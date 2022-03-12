package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
@SuppressWarnings(value = "unused")
class Number implements Serializable {

    @Min(1)
    @Column(nullable = false)
    private Integer numberValue;

    protected Number() {

    }

    private Number(Integer value) {

        this.numberValue = value;
    }

    public Number from(Integer value) {

        return new Number(value);
    }
}
