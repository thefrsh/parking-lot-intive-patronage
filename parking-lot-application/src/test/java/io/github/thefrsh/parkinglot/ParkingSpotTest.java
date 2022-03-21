package io.github.thefrsh.parkinglot;

import io.github.thefrsh.parkinglot.container.TestPostgresqlContainer;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.ParkingSpotPersistence;
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
import org.testcontainers.junit.jupiter.Container;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "classpath:db/data/clear-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingSpotTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @Autowired
    private ParkingSpotPersistence parkingSpotPersistence;

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
    void setupClass() {

        flyway.migrate();
    }

    @BeforeEach
    void setUp() {

        RestAssured.port = port;
    }

    @Test
    @DisplayName(value = "[Get parking spots] List of free parking spots -> Ok 200")
    void oneBookedParkingSpotIsGiven_whenGettingListOfFreeParkingSpots_thenReturnsOkAndParkingSpotsList() {

        final boolean bookable = true;

        bookableParkingSpotListTest(bookable);
    }

    @Test
    @DisplayName(value = "[Get parking spots] List of booked parking spots -> Ok 200")
    void oneBookedParkingSpotIsGiven_whenGettingListOfBookedParkingSpots_thenReturnsOkAndParkingSpotsList() {

        final boolean bookable = false;

        bookableParkingSpotListTest(bookable);
    }

    private void bookableParkingSpotListTest(boolean bookable) {

        var expectedParkingSpotIds = parkingSpotPersistence.loadAll()
                .filter(parkingSpot -> parkingSpot.bookable() == bookable)
                .map(ParkingSpot::getId)
                .map(Long::intValue)
                .toJavaArray();

        given()
                .basePath("/api/parking-spots/search/by-bookability")
                .param("bookable", bookable)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.parkingSpots.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parkingSpots.id", containsInAnyOrder(expectedParkingSpotIds));
    }
}
