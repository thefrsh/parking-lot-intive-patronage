package io.github.thefrsh.parkinglot;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.github.thefrsh.parkinglot.container.TestPostgresqlContainer;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookingPersistence;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;
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

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "classpath:db/data/clear-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @Autowired
    private BookingPersistence bookingPersistence;

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

        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.url", container::getJdbcUrl);
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

        final int bookerId = 2;
        final int parkingSpotId = 10;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Non-existing parking spot -> Not Found 404")
    void nonExistingParkingSpotIsGiven_whenBookingAttempt_thenReturnsNotFound() {

        final int bookerId = 1;
        final int parkingSpotId = 30;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Parking spot has been already booked -> Conflict 409")
    void alreadyBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsConflict() {

        final int bookerId = 1;
        final int parkingSpotId = 1;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Negative user Id -> Bad Request 400")
    void negativeUserIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

        final int bookerId = -1;
        final int parkingSpotId = 1;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Negative parking spot Id -> Bad Request 400")
    void negativeParkingSpotIdIsGiven_whenBookingAttempt_thenReturnsBadRequest() {

        final int bookerId = 1;
        final int parkingSpotId = -1;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName(value = "[Create booking] Existing user and free parking spot -> No Content 204")
    void existingUserIdAndNotBookedParkingSpotIsGiven_whenBookingAttempt_thenReturnsNoContent() {

        final int bookerId = 1;
        final int parkingSpotId = 2;

        given()
                .basePath(getBookActionPath(bookerId, parkingSpotId))
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName(value = "[Delete booking] Parking spot that is not owned by user -> Conflict 409")
    void notOwnedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsConflict() {

        final int bookerId = 1;
        final int parkingSpotId = 2;

        given()
                .basePath(getUnbookActionPath(bookerId, parkingSpotId))
        .when()
                .delete()
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName(value = "[Delete booking] Parking spot that is owned by user -> No Content 204")
    void ownedParkingSpotIsGiven_whenDeleteBookingAttempt_thenReturnsNoContent() {

        final int bookerId = 1;
        final int parkingSpotId = 1;

        given()
                .basePath(getUnbookActionPath(bookerId, parkingSpotId))
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
        var booker = bookerPersistence.loadById((long) userId);
        var booking = bookingPersistence.loadByBooker(booker);

        var expectedParkingSpotIds = booking.getBookedSpots()
                .map(ParkingSpot::getId)
                .map(Long::intValue)
                .toJavaArray();

        given()
               .basePath(getSearchByOwnerParkingSpotsPath())
               .param("id", userId)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.parkingSpots.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parkingSpots.id", containsInAnyOrder(expectedParkingSpotIds));
    }

    private String getBookActionPath(int bookerId, int parkingSpotId) {

        return "/api/users/%d/booked-spots/%d/book".formatted(bookerId, parkingSpotId);
    }

    private String getUnbookActionPath(int bookerId, int parkingSpotId) {

        return "/api/users/%d/booked-spots/%d/unbook".formatted(bookerId, parkingSpotId);
    }

    private String getSearchByOwnerParkingSpotsPath() {

        return "/api/parking-spots/search/by-owner";
    }
}
