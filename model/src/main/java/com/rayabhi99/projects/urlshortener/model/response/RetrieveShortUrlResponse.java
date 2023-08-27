package com.rayabhi99.projects.urlshortener.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * This class is response body to Retrieve Short URL service
 */
@Data
@Builder
@NonNull
public class RetrieveShortUrlResponse {
    private String shortUrl;
    private String longUrl;
    private String createdAt;
    private String updatedAt;
    private String expiryDate;
    private String status;
    private long apiHitCount;
}
