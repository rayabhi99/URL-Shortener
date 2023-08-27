package com.rayabhi99.projects.urlshortener.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@NonNull
public class ShortUrl {
    private String shortUrl;
    private String longUrl;
    private String createdAt;
    private String updatedAt;
    private String expiryDate;
    private String status;
    private long apiHitCount;
}
