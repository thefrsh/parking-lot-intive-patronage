package io.github.thefrsh.parkinglot;

import io.github.thefrsh.parkinglot.container.TestPostgresqlContainer;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.ParkingSpotPersistence;
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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@Testcontainers
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
    public static void overrideProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.flyway.url", container::getJdbcUrl);
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

        final boolean available = true;

        availableParkingSpotListTest(available);
    }

    @Test
    @DisplayName(value = "[Get parking spots] List of booked parking spots -> Ok 200")
    void oneBookedParkingSpotIsGiven_whenGettingListOfBookedParkingSpots_thenReturnsOkAndParkingSpotsList() {

        final boolean available = false;

        availableParkingSpotListTest(available);
    }

    private void availableParkingSpotListTest(boolean available) {

        var expectedParkingSpotIds = parkingSpotPersistence.loadAll()
                .filter(parkingSpot -> (parkingSpot.getOwner().isEmpty()) == available)
                .map(ParkingSpot::getId)
                .map(Long::intValue)
                .toJavaArray();

        var r = given()
                .basePath("/api/parking-spots/search/by-availability")
                .param("available", available)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("_embedded.parking-spots.size()", is(expectedParkingSpotIds.length))
                .body("_embedded.parking-spots.id", containsInAnyOrder(expectedParkingSpotIds));
    }
}
