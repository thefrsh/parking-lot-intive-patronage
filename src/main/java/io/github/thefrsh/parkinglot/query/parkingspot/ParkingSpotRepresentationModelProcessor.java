package io.github.thefrsh.parkinglot.query.parkingspot;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class ParkingSpotRepresentationModelProcessor
        implements RepresentationModelProcessor<EntityModel<ParkingSpotProjection>> {

    @NonNull
    @Override
    public EntityModel<ParkingSpotProjection> process(EntityModel<ParkingSpotProjection> model) {

        var entityModel = EntityModel.of(Objects.requireNonNull(model.getContent()));
        model.getLink(IanaLinkRelations.SELF).ifPresent(entityModel::add);

        return entityModel;
    }
}
