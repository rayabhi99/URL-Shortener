package com.rayabhi99.projects.urlshortener.app.utility;

import java.time.LocalDateTime;

public interface Utility {
    /**
     * Validates the URL. Throws exception, if it is invalid
     * @param url
     */
    public void validateUrl(String url);

    /**
     * Validates the ttl. Throws exception, if it is invalid
     * @param maxAllowedTTl
     * @param ttlInDays
     */
    public void validateTtl(String ttlInDays, int maxAllowedTTl);

    /**
     * Validates the status of the URL - "ACTIVE/INACTIVE"
     * @param status
     */
    public void validateStatus(String status);


    /**
     * This method creates a short Url from a long Url
     * @param longUrl
     * @return
     */
    public String createShortUrl(String longUrl, int shorUrlLength);

    /**
     * Returns Date-Time from ttl
     * @param ttlInDays
     * @return
     */
    public String getExpiryDateFrom(int ttlInDays);

    /**
     * Converts dateTime string to DateTime object
     * @param dateTime
     * @return
     */
    public LocalDateTime convertToDateTimeFrom(String dateTime);
}
