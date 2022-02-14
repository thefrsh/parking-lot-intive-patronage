package io.github.thefrsh.parkinglot.controller;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.service.ParkingSpotService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingSpotResponse> getParkingSpots(@RequestParam Boolean available) {

        return parkingSpotService.getParkingSpots(available);
    }
}
