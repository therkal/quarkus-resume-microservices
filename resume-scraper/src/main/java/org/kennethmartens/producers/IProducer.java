package org.kennethmartens.producers;

public interface IProducer<TObjectType> {
    void produce(TObjectType entity);
}
