package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
@SuppressWarnings(value = "unused")
class Storey implements Serializable {

    @Min(1)
    @Column(nullable = false)
    private Integer storeyValue;

    protected Storey() {

    }

    private Storey(Integer value) {

        this.storeyValue = value;
    }

    public Storey from(Integer value) {

        return new Storey(value);
    }
}
