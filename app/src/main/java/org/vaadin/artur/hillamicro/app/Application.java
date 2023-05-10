package org.vaadin.artur.hillamicro.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.NoTheme;

@SpringBootApplication
@NoTheme
@ComponentScan(basePackageClasses = { MessagingConfiguration.class,
        Application.class })
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
