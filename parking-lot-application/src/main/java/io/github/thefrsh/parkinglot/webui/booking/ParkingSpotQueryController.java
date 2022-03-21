package io.github.thefrsh.parkinglot.webui.booking;

import io.github.thefrsh.dddcqrs.query.annotation.Finder;
import io.github.thefrsh.parkinglot.application.troubleshooting.ErrorResponse;
import io.github.thefrsh.parkinglot.infrastructure.persistence.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Finder
@RestController
@RequestMapping(path = "/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Parking spot", description = "Parking spot query Api")
class ParkingSpotQueryController {

    private final ParkingSpotQueryRepository queryRepository;

    @Operation(
            summary = "Single parking spot",
            description = "Gets parking spot response entity defined by its id",
            tags = "Parking spot",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Parking spot identifier",
                            required = true,
                            in = ParameterIn.QUERY
                    )
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Parking spot is found",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ParkingSpotProjection.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Parking spot is not found",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ParkingSpotProjection> findById(@PathVariable Long id) {

        var parkingSpot = queryRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                "Parking spot with id %d is not found".formatted(id)
        ));

        var selfLink = linkTo(methodOn(ParkingSpotQueryController.class).findById(id)).withSelfRel();
        parkingSpot.add(selfLink);

        return ResponseEntity.ok(parkingSpot);
    }

    /*
    ===============================================================================================================
     */

    @Operation(
            summary = "Available/not available parking spots",
            description = "Gets list of parking spots depending on availability",
            tags = "Parking spot",
            parameters = {
                    @Parameter(
                            name = "available",
                            description = "Decides whether returned parking spots to be available or not",
                            required = true,
                            in = ParameterIn.QUERY
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of available/not available parking spots (can be empty)",
            content = @Content(
                    mediaType = MediaTypes.HAL_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ParkingSpotProjection.class))
            )
    )
    @GetMapping(path = "/search/by-bookability", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<ParkingSpotProjection>> findAllByBookability(
            @RequestParam Boolean bookable
    ) {

        var parkingSpots = queryRepository.findAllByBookability(bookable);

        for(var parkingSpot : parkingSpots) {

            var singleSelfLink = linkTo(methodOn(ParkingSpotQueryController.class)
                    .findById(parkingSpot.getId()))
                    .withSelfRel();

            parkingSpot.add(singleSelfLink);
        }

        var selfLink = linkTo(methodOn(ParkingSpotQueryController.class)
                .findAllByBookability(bookable))
                .withSelfRel();

        var collectionModel = CollectionModel.of(parkingSpots, selfLink);

        return ResponseEntity.ok(collectionModel);
    }

    /*
    ===============================================================================================================
     */

    @Operation(
            summary = "Parking spots by its owner id",
            description = "Get list of parking spots by provided owner id",
            tags = "Parking spot",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Owner identifier",
                            required = true,
                            in = ParameterIn.QUERY
                    )
            }
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of parking spots owned by user (can be empty)",
            content = @Content(
                    mediaType = MediaTypes.HAL_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ParkingSpotProjection.class))
            )
    )
    @GetMapping(path = "/search/by-owner", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<ParkingSpotProjection>> findAllByOwnerId(@RequestParam Long id) {

        var parkingSpots = queryRepository.findAllByOwnerId(id);

        for(var parkingSpot : parkingSpots) {

            var singleSelfLink = linkTo(methodOn(ParkingSpotQueryController.class)
                    .findById(parkingSpot.getId()))
                    .withSelfRel();

            parkingSpot.add(singleSelfLink);
        }

        var selfLink = linkTo(methodOn(ParkingSpotQueryController.class)
                .findAllByOwnerId(id))
                .withSelfRel();

        var collectionModel = CollectionModel.of(parkingSpots, selfLink);

        return ResponseEntity.ok(collectionModel);
    }
}
