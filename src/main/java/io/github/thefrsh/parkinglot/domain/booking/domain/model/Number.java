package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Min;

@Embeddable
@ValueObject
class Number {

    @Min(1)
    @Column(nullable = false)
    private Integer number;

    protected Number() {

    }

    private Number(Integer value) {

        this.number = value;
    }

    public Number from(Integer value) {

        return new Number(value);
    }
}
