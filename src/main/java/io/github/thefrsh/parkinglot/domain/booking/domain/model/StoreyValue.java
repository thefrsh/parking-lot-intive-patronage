package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@ValueObject
@SuppressWarnings(value = "unused")
class StoreyValue implements Serializable {

    @Min(1)
    @Column(nullable = false)
    private Integer storey;

    protected StoreyValue() {

    }

    private StoreyValue(Integer value) {

        this.storey = value;
    }

    public StoreyValue from(Integer value) {

        return new StoreyValue(value);
    }
}
