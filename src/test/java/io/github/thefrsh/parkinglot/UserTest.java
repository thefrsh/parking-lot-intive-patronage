package io.github.thefrsh.parkinglot;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.github.thefrsh.parkinglot.container.TestPostgresqlContainer;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.restassured.RestAssured;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "classpath:db/data/clear-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @Autowired
    private BookerPersistence bookerPersistence;

    @Container
    private static final TestPostgresqlContainer container;

    static {

        container = new TestPostgresqlContainer("postgres");
        container.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.flyway.url", container::getJdbcUrl);
    }

    @BeforeAll
    void beforeClass() {

        flyway.migrate();
    }

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
    }

    @AfterAll
    void afterClass() {

        flyway.clean();
    }

    @Test
    @DisplayName(value = "[Create booking] Non-existing user -> Not Found 404")
    void nonExistingUserIsGiven_whenBookingAttempt_thenReturnsNotFound() {

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
    void nonExistingParkingSpotIsGiven_whenBookingAttempt_thenReturnsNotFound() {

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
    void alreadyBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsConflict() {

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
    void negativeUserIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

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
    void negativeParkingSpotIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

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
    void existingUserIdAndNotBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsNoContent() {

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
    void notOwnedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsConflict() {

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
    void ownedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsNoContent() {

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
    void userOwningOneParkingSpotIsGiven_whenGettingBookedSpots_shouldReturnOkAndParkingSpotsList() {

        final int userId = 1;
        var user = bookerPersistence.loadById((long) userId);

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
                .body("_embedded.parking-spots.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parking-spots.id", containsInAnyOrder(expectedParkingSpotIds));
    }

    private String getBookingPath(int userId, int parkingSpotId) {

        return String.format("/api/users/%d/booked-spots/%d", userId, parkingSpotId);
    }

    private String getBookedPath() {

        return "/api/parking-spots/search/by-owner";
    }
}
