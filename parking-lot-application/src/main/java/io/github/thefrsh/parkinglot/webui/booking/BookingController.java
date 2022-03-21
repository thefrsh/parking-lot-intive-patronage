package io.github.thefrsh.parkinglot.webui.booking;

import io.github.thefrsh.parkinglot.application.booking.BookingApplicationService;
import io.github.thefrsh.parkinglot.application.troubleshooting.ErrorResponse;
import io.github.thefrsh.parkinglot.webui.booking.ParkingSpotQueryController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
@Tag(name = "Booking", description = "Operations about Booking domain")
class BookingController {

    private final BookingApplicationService applicationService;

    @Operation(
            summary = "Creates booking",
            description = "Creates booking between user and parking spot defined by Ids",
            tags = "Booking",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "User identifier",
                            required = true,
                            in = ParameterIn.PATH
                    ),
                    @Parameter(
                            name = "spotId",
                            description = "Parking spot identifier",
                            required = true,
                            in = ParameterIn.PATH
                    )
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Parking spot has been successfully booked"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Parking spot is already booked by other user",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Provided id is less than 1",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @PostMapping(path = "/{userId}/booked-spots/{spotId}/book")
    public ResponseEntity<Void> createBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        applicationService.createBooking(userId, spotId);

        var location = linkTo(methodOn(ParkingSpotQueryController.class).findAllByOwnerId(userId)).toUri();
        return ResponseEntity.created(location).build();
    }

    /*
    ===============================================================================================================
     */

    @Operation(
            summary = "Deletes booking",
            description = "Deletes booking between user and parking spot defined by Ids",
            tags = "Booking",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "User identifier",
                            required = true,
                            in = ParameterIn.PATH),
                    @Parameter(
                            name = "spotId",
                            description = "Parking spot identifier",
                            required = true,
                            in = ParameterIn.PATH
                    )
            }
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Booking has been successfully deleted"),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Parking spot has not been booked by user",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Provided id is less than 1",
                            content = @Content(
                                    mediaType = MediaTypes.HAL_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @DeleteMapping(path = "/{userId}/booked-spots/{spotId}/unbook")
    public ResponseEntity<Object> deleteBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        applicationService.deleteBooking(userId, spotId);
        return ResponseEntity.noContent().build();
    }
}
