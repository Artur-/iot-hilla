package org.vaadin.artur.hillamicro.servertime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration;
import org.vaadin.artur.hillamicro.shared.MessagingConfiguration.ApplicationInfo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.NoTheme;

@SpringBootApplication
@NoTheme
@ComponentScan(basePackageClasses = { MessagingConfiguration.class,
    Application.class })
public class Application implements AppShellConfigurator {

    public static final ApplicationInfo INFO = new ApplicationInfo(
            "server-time", "Server Time", "/server-time", "",
            "/server-time/VAADIN/build/server-time.js");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
