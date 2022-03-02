package org.kennethmartens.producers;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.kennethmartens.models.Position;

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
