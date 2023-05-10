package org.vaadin.artur.hillamicro.servertime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.NoTheme;

@SpringBootApplication
@NoTheme
public class Application implements AppShellConfigurator {

    public static final String ID = "room-info";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
