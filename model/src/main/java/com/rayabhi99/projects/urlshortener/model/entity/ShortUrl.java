package com.rayabhi99.projects.urlshortener.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NonNull
@Document(collection = "shortUrl")
public class ShortUrl {
    @Id
    private String shortUrl;
    private String longUrl;
    private String createdAt;
    private String updatedAt;
    private String expiryDate;
    private String status;
    private long apiHitCount;
}
