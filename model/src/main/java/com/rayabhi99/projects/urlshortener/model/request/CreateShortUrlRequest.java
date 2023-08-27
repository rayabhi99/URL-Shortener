package com.rayabhi99.projects.urlshortener.model.request;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * This class is the request body to Create New Short URL Service
 */
@Data
public class CreateShortUrlRequest {
    @NonNull
    private String longUrl;
    @Nullable
    private String ttlInDays;
}
