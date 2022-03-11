package io.github.thefrsh.parkinglot;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @Autowired
    private BookerPersistence userPersistence;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
    }

    @Test
    @DisplayName(value = "[Create booking] Non-existing user -> Not Found 404")
    public void nonExistingUserIsGiven_whenBookingAttempt_thenReturnsNotFound() {

        final int userId = 2;
        final int parkingSpotId = 10;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Non-existing parking spot -> Not Found 404")
    public void nonExistingParkingSpotIsGiven_whenBookingAttempt_thenReturnsNotFound() {

        final int userId = 1;
        final int parkingSpotId = 30;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Parking spot has been already booked -> Conflict 409")
    public void alreadyBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsConflict() {

        final int userId = 1;
        final int parkingSpotId = 1;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Negative user Id -> Bad Request 400")
    public void negativeUserIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

        final int userId = -1;
        final int parkingSpotId = 1;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Negative parking spot Id -> Bad Request 400")
    public void negativeParkingSpotIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

        final int userId = 1;
        final int parkingSpotId = -1;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Existing user and free parking spot -> No Content 204")
    public void existingUserIdAndNotBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsNoContent() {

        final int userId = 1;
        final int parkingSpotId = 2;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName(value = "[Delete booking] Parking spot that is not owned by user -> Conflict 409")
    public void notOwnedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsConflict() {

        final int userId = 1;
        final int parkingSpotId = 2;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName(value = "[Delete booking] Parking spot that is owned by user -> No Content 204")
    public void ownedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsNoContent() {

        final int userId = 1;
        final int parkingSpotId = 1;

        given()
                .basePath(getBookingPath(userId, parkingSpotId))
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName(value = "[Get booked list] User owning one parking spot -> Ok 200")
    @Transactional
    public void userOwningOneParkingSpotIsGiven_whenGettingBookedSpots_shouldReturnOkAndParkingSpotsList() {

        final int userId = 1;
        var user = userPersistence.loadById((long) userId);

        var expectedParkingSpotIds = user.getBookedSpots()
                .map(ParkingSpot::getId)
                .map(Long::intValue)
                .toJavaArray();

        given()
               .basePath(getBookedPath())
               .param("id", userId)
        .when()
               .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.parking-spot.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parking-spot.id", containsInAnyOrder(expectedParkingSpotIds));
    }

    private String getBookingPath(int userId, int parkingSpotId) {

        return String.format("/api/users/%d/booked-spots/%d", userId, parkingSpotId);
    }

    private String getBookedPath() {

        return "/api/parking-spots/search/by-owner";
    }
}
