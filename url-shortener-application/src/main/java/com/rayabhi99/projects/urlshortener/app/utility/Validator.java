package com.rayabhi99.projects.urlshortener.app.utility;

public interface Validator {
    /**
     * Validates the URL. Throws exception, if it is invalid
     * @param url
     */
    public void validateUrl(String url);

    /**
     * Validates the ttl. Throws exception, if it is invalid
     * @param ttlInDays
     */
    public void validateTtl(String ttlInDays);
}
