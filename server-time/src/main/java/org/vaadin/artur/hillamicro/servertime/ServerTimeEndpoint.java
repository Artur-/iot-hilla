package org.vaadin.artur.hillamicro.servertime;

import java.time.Duration;
import java.time.LocalDateTime;

import dev.hilla.Endpoint;
import reactor.core.publisher.Flux;

import com.vaadin.flow.server.auth.AnonymousAllowed;

@Endpoint
@AnonymousAllowed
public class ServerTimeEndpoint {

    public Flux<LocalDateTime> subscribe() {
        Flux<LocalDateTime> f = Flux.interval(Duration.ofSeconds(1))
                .map(l -> getServerTime());
        return f;
    }

    public LocalDateTime getServerTime() {
        return LocalDateTime.now();
    }
}
