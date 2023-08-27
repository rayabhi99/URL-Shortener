package com.rayabhi99.projects.urlshortener.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * This class is the response body to Delete Url Service
 */
@Data
@Builder
@NonNull
public class DeleteUrlResponse {
    private String shortUrl;
    private String longUrl;
    private String status;
}
