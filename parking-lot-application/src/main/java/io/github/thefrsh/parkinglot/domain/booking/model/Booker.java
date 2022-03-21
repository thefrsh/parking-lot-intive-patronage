package io.github.thefrsh.parkinglot.domain.booking.model;

import io.github.thefrsh.dddcqrs.domain.BaseEntity;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * User entity representing parking spot booker
 * @author Michal Broniewicz
 */
@Entity
@NoArgsConstructor
@Table(name = "users")
@DomainEntity
@SuppressWarnings(value = "unused")
public class Booker extends BaseEntity {

    @OneToOne(mappedBy = "booker")
    private Booking booking;
}
