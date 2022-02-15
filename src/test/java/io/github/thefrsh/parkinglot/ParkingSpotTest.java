package io.github.thefrsh.parkinglot;

import io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository.ParkingSpotRepository;
import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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

        var expectedParkingSpotIds = parkingSpotRepository.findAll()
                .filter(parkingSpot -> (parkingSpot.getOwner().isEmpty()) == available)
                .map(ParkingSpot::getId)
                .map(Long::intValue)
                .toJavaArray();

        given()
                .basePath("/api/parking-spots/search/by-availability")
                .param("available", available)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.parkingSpot.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parkingSpot.id", containsInAnyOrder(expectedParkingSpotIds));
    }
}
