package io.github.thefrsh.parkinglot.controller;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.service.ParkingSpotService;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/parking-spots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Autowired
    public ParkingSpotController(ParkingSpotService parkingSpotService) {

        this.parkingSpotService = parkingSpotService;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = {"/", ""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingSpotResponse> getParkingSpots(@RequestParam Boolean available) {

        return parkingSpotService.getParkingSpots(available);
    }
}
