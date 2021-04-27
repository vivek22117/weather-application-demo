package com.weather.api;

import com.weather.api.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@Import(SwaggerConfig.class)
public class WeatherAPI {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "local");

        SpringApplication.run(WeatherAPI.class, args);
    }

}
