package com.rayabhi99.projects.urlshortener.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This class holds the properties mentioned in configuration
 */

@Component
@ComponentScan(basePackages = "com.rayabhi99.projects.urlshortener")
@ConfigurationProperties(prefix = "propertyholder")
@Scope("singleton")
@Data
public class PropertyHolder {
    private int defaultTtlInDays;
    private int maxTtlInDaysAllowed;
}
