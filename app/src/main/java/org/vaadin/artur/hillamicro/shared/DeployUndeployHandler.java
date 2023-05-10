package org.vaadin.artur.hillamicro.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.ApplicationInfo;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.DeploymentInfo;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.Type;

@Service
public class DeployUndeployHandler {

    private Logger logger = LoggerFactory
            .getLogger(DeployUndeployHandler.class);

    @Autowired
    private ApplicationInfo applicationInfo;

    @Autowired
    private RabbitTemplate template;

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent contextClosedEvent) {
        try {
            template.convertAndSend(MessagingConfiguration.EXCHANGE,
                    MessagingConfiguration.ROUTING_KEY,
                    new DeploymentInfo(Type.UNDEPLOY, applicationInfo));
            logger.info("Undeploy event sent");
        } catch (Exception e) {
            logger.info("Error sending undeploy event", e);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(
            ApplicationReadyEvent applicationReadyEvent) {
        try {

            template.convertAndSend(MessagingConfiguration.EXCHANGE,
                    MessagingConfiguration.ROUTING_KEY,
                    new DeploymentInfo(Type.DEPLOY, applicationInfo));
            logger.info("Deploy event sent");
        } catch (Exception e) {
            logger.info("Error sending deploy event", e);
        }
    }

}
