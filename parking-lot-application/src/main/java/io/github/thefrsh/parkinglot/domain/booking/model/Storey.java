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
class Storey implements Serializable {

    @Min(1)
    @Column(name = "storey", nullable = false)
    private Integer storeyValue;

    private Storey(Integer value) {

        this.storeyValue = value;
    }

    public Storey from(Integer value) {

        return new Storey(value);
    }
}
