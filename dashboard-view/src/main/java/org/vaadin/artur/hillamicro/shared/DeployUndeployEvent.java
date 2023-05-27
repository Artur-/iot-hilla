package org.vaadin.artur.hillamicro.shared;

import org.springframework.context.ApplicationEvent;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.DeploymentInfo;

public class DeployUndeployEvent extends ApplicationEvent {

    private DeploymentInfo deploymentInfo;

    public DeployUndeployEvent(Object source, DeploymentInfo deploymentInfo) {
        super(source);
        this.deploymentInfo = deploymentInfo;
    }

    public DeploymentInfo getDeploymentInfo() {
        return deploymentInfo;
    }
}
