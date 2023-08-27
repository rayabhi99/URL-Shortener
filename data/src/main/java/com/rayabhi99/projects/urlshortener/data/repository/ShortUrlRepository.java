package com.rayabhi99.projects.urlshortener.data.repository;

import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
}