package com.rayabhi99.projects.urlshortener.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * This class is the response body to Create New Short URL Service
 */
@Data
@Builder
public class CreateShortUrlResponse {
    private String shortUrl;
    private String longUrl;
    private String createdAt;
    private String expiryDate;
    private String status;

}
