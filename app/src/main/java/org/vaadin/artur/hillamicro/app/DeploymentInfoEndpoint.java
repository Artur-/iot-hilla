package org.vaadin.artur.hillamicro.app;

import java.util.HashSet;
import java.util.Set;

import dev.hilla.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.ApplicationInfo;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.DeploymentInfo;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.Type;
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
    private final Set<ApplicationInfo> deployed = new HashSet<>();

    @RabbitListener(queues = MessagingConfiguration.QUEUE)
    public void appDeployed(DeploymentInfo info) {
        deploymentSink.emitNext(info, (signalType,
                emitResult) -> emitResult == EmitResult.FAIL_NON_SERIALIZED);

        if (info.type() == Type.DEPLOY) {
            deployed.add(info.applicationInfo());
            logger.info("App deployed: " + info.applicationInfo().title());
        } else {
            deployed.remove(info.applicationInfo());
            logger.info("App undeployed: " + info.applicationInfo().title());
        }
    }

    @AnonymousAllowed
    public Flux<DeploymentInfo> getDeploymentUpdates() {
        DeploymentInfo[] currentApps = deployed.stream()
                .map(appInfo -> new DeploymentInfo(Type.DEPLOY, appInfo))
                .toArray(DeploymentInfo[]::new);
        logger.info("Current apps");
        for (DeploymentInfo app : currentApps) {
            logger.info(app.toString());
        }
        logger.info("Current apps done");
        Flux<DeploymentInfo> current = Flux.just(currentApps);
        return Flux.concat(current, updates);
    }
}
