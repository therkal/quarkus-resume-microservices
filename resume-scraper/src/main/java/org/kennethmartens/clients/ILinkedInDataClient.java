package org.kennethmartens.clients;

import io.smallrye.mutiny.Uni;


public interface ILinkedInDataClient {
    Uni<String> findLinkedInProfile(String profileName);
}
