package com.rayabhi99.projects.urlshortener.data.impl;

import com.mongodb.MongoException;
import com.rayabhi99.projects.urlshortener.data.ShortUrlDataService;
import com.rayabhi99.projects.urlshortener.data.repository.ShortUrlRepository;
import com.rayabhi99.projects.urlshortener.model.Status;
import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import com.rayabhi99.projects.urlshortener.model.error.ErrorCode;
import com.rayabhi99.projects.urlshortener.model.error.UrlShortenerException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Retryable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Implements the short url service interface methods to do IO with DB
 */

@Service
public class ShortUrlDataServiceImpl implements ShortUrlDataService {
    private final ShortUrlRepository shortUrlRepository;
    private final int MAX_RETRIES = 3;

    public ShortUrlDataServiceImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    /**
     * Searches the DB by primary ID as shortUrl
     * @param shortUrl
     * @return
     * @throws Exception
     */
    @Retryable(maxAttempts = MAX_RETRIES, include = {MongoException.class})
    @Override
    public ShortUrl searchUrl(String shortUrl) throws Exception {
        Optional<ShortUrl> shortUrlOptional;
        try {
            shortUrlOptional = shortUrlRepository.findById(shortUrl);
        }
        catch (MongoException e)
        {
            throw new UrlShortenerException(ErrorCode.DB_SEARCH_ERROR, "Not able to search the DB", e);
        }

        if(shortUrlOptional.isPresent())
            return shortUrlOptional.get();
        else throw new UrlShortenerException(ErrorCode.URL_NOT_FOUND_ERROR, "The URL is not found in DB");
    }

    /**
     * Returns all possible entries for a user
     * @return
     * @throws Exception
     */
    @Retryable(maxAttempts = MAX_RETRIES, include = {MongoException.class})
    @Override
    public List<ShortUrl> getAllUrls() throws Exception {
        return null;
    }

    /**
     * Inserts new entry to DB
     * @param shortUrl
     * @param longUrl
     * @param ttlInDays
     * @return
     * @throws Exception
     */
    @Retryable(maxAttempts = MAX_RETRIES, include = {MongoException.class})
    @Override
    public ShortUrl insertNewUrl(@NonNull String shortUrl, String longUrl, int ttlInDays) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        try {
            return shortUrlRepository.save(ShortUrl.builder().
                    shortUrl(shortUrl).
                    longUrl(longUrl).
                    createdAt(currentDateTime.toString()).
                    updatedAt(currentDateTime.toString()).
                    expiryDate(getExpiryDateFrom(ttlInDays, currentDateTime)).
                    status(Status.ACTIVE.name()).
                    apiHitCount(0).
                    build());
        }
        catch (MongoException e)
        {
            throw new UrlShortenerException(ErrorCode.DB_INSERTION_ERROR, "Not able to insert data into DB", e);
        }
    }

    /**
     * Updates existing entries in DB
     * @param shortUrl
     * @return
     * @throws Exception
     */
    @Retryable(maxAttempts = MAX_RETRIES, include = {MongoException.class})
    @Override
    public ShortUrl updateShortUrl(ShortUrl shortUrl) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        ShortUrl shortUrl1 = searchUrl(shortUrl.getShortUrl());
        try {
            return shortUrlRepository.save(ShortUrl.builder().
                    shortUrl(shortUrl1.getShortUrl()).
                    longUrl(shortUrl1.getLongUrl()).
                    createdAt(shortUrl1.getCreatedAt()).
                    updatedAt(currentDateTime.toString()).
                    expiryDate(shortUrl.getExpiryDate()).
                    status(shortUrl.getStatus()).
                    apiHitCount(shortUrl1.getApiHitCount()).
                    build());
        }
        catch (MongoException e)
        {
            throw new UrlShortenerException(ErrorCode.DB_UPDATE_ERROR, "Not able to update data into DB", e);
        }
    }

    /**
     * Deletes any existing entry
     * @param shortUrl
     * @throws Exception
     */
    @Retryable(maxAttempts = MAX_RETRIES, include = {MongoException.class})
    @Override
    public void deleteShortUrl(String shortUrl) throws Exception {
        ShortUrl shortUrl1 = searchUrl(shortUrl);
        try {
            shortUrlRepository.delete(shortUrl1);
        }
        catch (MongoException e)
        {
            throw new UrlShortenerException(ErrorCode.DB_UPDATE_ERROR, "Not able to update data into DB", e);
        }
    }


    private String getExpiryDateFrom(int ttlInDays, LocalDateTime currentDate) {
        return currentDate.plusDays(ttlInDays).toString();
    }


    private LocalDateTime convertToDateTimeFrom(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTime, formatter);
    }

}
