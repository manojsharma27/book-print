package com.ms.printing.bookprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ms.printing.bookprint.repositories" })
@EntityScan(basePackages = { "com.ms.printing.bookprint.repositories.entities" })
@PropertySource(value = {"classpath:/application.properties"}, ignoreResourceNotFound = true)
@EnableSpringDataWebSupport
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
