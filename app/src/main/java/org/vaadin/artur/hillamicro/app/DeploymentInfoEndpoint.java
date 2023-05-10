package org.vaadin.artur.hillamicro.app;

import dev.hilla.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.vaadin.artur.hillamicro.app.MessagingConfiguration.DeploymentInfo;
import org.vaadin.artur.hillamicro.app.MessagingConfiguration.Type;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

import com.vaadin.flow.server.auth.AnonymousAllowed;

@Endpoint
public class DeploymentInfoEndpoint {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Many<DeploymentInfo> deploymentSink = Sinks.many().multicast()
            .directBestEffort();
    private final Flux<DeploymentInfo> updates = deploymentSink.asFlux();

    @RabbitListener(queues = MessagingConfiguration.QUEUE)
    public void appDeployed(DeploymentInfo info) {
        deploymentSink.emitNext(info, (signalType,
                emitResult) -> emitResult == EmitResult.FAIL_NON_SERIALIZED);

        if (info.type() == Type.DEPLOY) {
            logger.info("App deployed: " + info.app());
        } else {
            logger.info("App undeployed: " + info.app());
        }
    }

    @AnonymousAllowed
    public Flux<DeploymentInfo> getDeploymentUpdates() {
        return updates;
    }
}
