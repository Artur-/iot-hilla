package com.example.application;

import com.example.application.MessagingConfiguration.DeploymentInfo;
import com.example.application.MessagingConfiguration.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DeployUndeployHandler {

    private Logger logger = LoggerFactory
            .getLogger(DeployUndeployHandler.class);

    @Autowired
    private RabbitTemplate template;

    @EventListener(ContextClosedEvent.class)
    public void onContextClosedEvent(ContextClosedEvent contextClosedEvent) {
        try {
            template.convertAndSend(MessagingConfiguration.EXCHANGE,
                    MessagingConfiguration.ROUTING_KEY,
                    new DeploymentInfo(Type.UNDEPLOY, Application.ID));
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
                    new DeploymentInfo(Type.DEPLOY, Application.ID));
            logger.info("Deploy event sent");
        } catch (Exception e) {
            logger.info("Error sending deploy event", e);
        }
    }

}
