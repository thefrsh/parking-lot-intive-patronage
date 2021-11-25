package io.github.thefrsh.parkinglot;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.model.ParkingSpot;
import io.github.thefrsh.parkinglot.model.repository.ParkingSpotRepository;
import io.restassured.RestAssured;
import io.vavr.collection.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;

import static io.restassured.RestAssured.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingSpotTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
    }

    @Test
    @DisplayName(value = "[Get parking spots] List of free parking spots -> Ok 200")
    public void oneBookedParkingSpotIsGiven_whenGettingListOfFreeParkingSpots_thenReturnsOkAndParkingSpotsList() {

        final boolean available = true;

        availableParkingSpotListTest(available);
    }

    @Test
    @DisplayName(value = "[Get parking spots] List of booked parking spots -> Ok 200")
    public void oneBookedParkingSpotIsGiven_whenGettingListOfBookedParkingSpots_thenReturnsOkAndParkingSpotsList() {

        final boolean available = false;

        availableParkingSpotListTest(available);
    }

    private void availableParkingSpotListTest(boolean available) {

        var expectedParkingSpots = parkingSpotRepository.findAll()
                .filter(parkingSpot -> (parkingSpot.getOwner() == null) == available)
                .map(ParkingSpot::getId);

        var parkingSpots = Arrays.stream(
                given()
                        .basePath("/api/parking-spots")
                        .param("available", available)
                .when()
                        .get()
                .then()
                        .statusCode(HttpStatus.OK.value())
                        .and()
                        .extract()
                        .as(ParkingSpotResponse[].class))
                .map(ParkingSpotResponse::getId)
                .collect(List.collector());

        Assertions.assertEquals(expectedParkingSpots.size(), parkingSpots.size());
        Assertions.assertTrue(expectedParkingSpots.containsAll(parkingSpots));
    }
}
