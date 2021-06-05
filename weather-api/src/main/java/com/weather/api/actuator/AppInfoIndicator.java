package com.weather.api.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppInfoIndicator implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("Application", "weather-api");
        builder.withDetail("Author", "Vivek Mishra");
        builder.withDetail("Version", "v1");
        builder.withDetail("Description", "Application to provide weather API based on city name!");
        builder.build();

        log.info("Application info has been configured!");
    }
}
