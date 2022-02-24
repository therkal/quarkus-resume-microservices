package org.kennethmartens.clients;

import io.smallrye.faulttolerance.api.CircuitBreakerName;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class LinkedInClient {

    // TODO: Uncomment
//    private final LinkedInRestClient client;
//
//    public LinkedInClient(@RestClient LinkedInRestClient client) {
//        this.client = client;
//    }

    @Inject
    LinkedInLocalHttpClient client;

    @CircuitBreaker(requestVolumeThreshold = 8, failureRatio = 0.5, delay = 2, delayUnit = ChronoUnit.SECONDS)
    @CircuitBreakerName("findLinkedInProfile")
    private CompletionStage<String> findLinkedInProfile(String profileName) {
        return this.client.findLinkedInProfile(profileName)
                .subscribeAsCompletionStage();
    }

    /**
     * Tries to fetch linked in profile with specified profile stringThe retry logic is applied to the result of the {@link CircuitBreaker}, meaning that retries that return failures could trigger the breaker to open.
     * @param profileName the profile string
     * @return
     */
    public Uni<Document> getLinkedInProfile(String profileName) {
        // The CompletionState is important so that on retry the Uni re-subscribes to a new
        // CompletionStage rather than the original one (which has already completed)
        return Uni.createFrom().completionStage(this.findLinkedInProfile(profileName))
                // Parse raw string returned to JSOUP HTML Document
                .onItem().transform(Jsoup::parse)
                .onFailure().retry().withBackOff(Duration.ofMillis(200)).atMost(3);
    }
}
