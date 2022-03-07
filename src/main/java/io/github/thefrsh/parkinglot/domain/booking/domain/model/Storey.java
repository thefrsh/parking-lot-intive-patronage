package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
class Storey implements Serializable {

    @Min(1)
    @Column(nullable = false)
    private Long value;

    protected Storey() {

    }

    private Storey(Long value) {

        this.value = value;
    }

    public Storey from(Long value) {

        return new Storey(value);
    }
}
