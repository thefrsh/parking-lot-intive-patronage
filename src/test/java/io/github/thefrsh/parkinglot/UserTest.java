package io.github.thefrsh.parkinglot;

import static io.restassured.RestAssured.*;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.model.repository.UserRepository;
import io.restassured.RestAssured;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Assertions;
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
    private UserRepository userRepository;

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
                .patch()
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
                .patch()
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
                .patch()
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
                .patch()
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
                .patch()
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
                .patch()
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
        var user = userRepository.findById((long) userId).get();

        var bookedSpots =
        given()
                .basePath(getBookedPath(userId))
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ParkingSpotResponse[].class);

        Assertions.assertEquals(user.getBookedSpots().size(), bookedSpots.length);

        Stream.ofAll(user.getBookedSpots())
                .zipWithIndex()
                .forEach(tuple -> {

                    var expected = tuple._1;
                    var actual = bookedSpots[tuple._2];

                    Assertions.assertEquals(expected.getId(), actual.getId());
                    Assertions.assertEquals(expected.getNumber(), actual.getNumber());
                    Assertions.assertEquals(expected.getStorey(), actual.getStorey());
                    Assertions.assertEquals(expected.getDisability(), actual.getDisability());
                });
    }

    private String getBookingPath(int userId, int parkingSpotId) {

        return String.format("/api/users/%d/booked-spots/%d", userId, parkingSpotId);
    }

    private String getBookedPath(int userId) {

        return String.format("/api/users/%d/booked-spots", userId);
    }
}
