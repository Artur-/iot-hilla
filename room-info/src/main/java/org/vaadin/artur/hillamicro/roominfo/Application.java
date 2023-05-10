package org.vaadin.artur.hillamicro.roominfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

    @Bean
    ApplicationInfo applicationInfo() {
        return ApplicationInfo.byConvention("room-info",null);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
