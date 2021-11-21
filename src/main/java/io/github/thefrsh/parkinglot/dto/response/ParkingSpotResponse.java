package io.github.thefrsh.parkinglot.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParkingSpotResponse {

    private Long id;
    private Integer number;
    private Integer storey;
    private Boolean disability;
}
