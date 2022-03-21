package io.github.thefrsh.parkinglot.domain.booking.model;

import io.github.thefrsh.dddcqrs.domain.annotation.ValueObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
@SuppressWarnings(value = "unused")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Number implements Serializable {

    @Min(1)
    @Column(name = "number", nullable = false)
    private Integer numberValue;

    private Number(Integer value) {

        this.numberValue = value;
    }

    public Number from(Integer value) {

        return new Number(value);
    }
}
