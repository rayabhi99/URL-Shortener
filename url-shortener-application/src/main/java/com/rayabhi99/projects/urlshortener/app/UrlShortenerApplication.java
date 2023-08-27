package com.rayabhi99.projects.urlshortener.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * This class is the starting point of the Url Shortener Application - http://localhost:8080/app/swagger-ui/index.html
 */

@OpenAPIDefinition (
        servers = @Server(url = "/app"),
        info = @Info(
                title = "URL Shortener Application",
                version = "1.0",
                description = "API docs for URL Shortener",
                contact = @Contact(
                        name = "URL Shortener Support",
                        email = "official.acc.abhisek@gamil.com"
                )
        )
)

@SpringBootApplication(scanBasePackages = {"com.rayabhi99.projects.urlshortener.app"})
@EnableConfigurationProperties

public class UrlShortenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}