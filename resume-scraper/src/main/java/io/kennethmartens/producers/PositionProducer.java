package io.kennethmartens.producers;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.kennethmartens.models.Position;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PositionProducer implements IProducer<Position> {

    @Channel("positions")
    Emitter<Position> positionEmitter;

    @Override
    public void produce(Position entity) {
        positionEmitter.send(entity);
    }

}
