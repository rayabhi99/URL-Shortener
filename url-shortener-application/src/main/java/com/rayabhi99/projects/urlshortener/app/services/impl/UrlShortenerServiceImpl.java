package com.rayabhi99.projects.urlshortener.app.services.impl;

import com.rayabhi99.projects.urlshortener.app.services.UrlShortenerService;
import com.rayabhi99.projects.urlshortener.app.utility.Utility;
import com.rayabhi99.projects.urlshortener.data.ShortUrlDataService;
import com.rayabhi99.projects.urlshortener.model.PropertyHolder;
import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import com.rayabhi99.projects.urlshortener.model.error.ErrorCode;
import com.rayabhi99.projects.urlshortener.model.error.UrlShortenerException;
import com.rayabhi99.projects.urlshortener.model.request.CreateShortUrlRequest;
import com.rayabhi99.projects.urlshortener.model.response.CreateShortUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.DeleteUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.RetrieveShortUrlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;

/**
 * This class provides implementation to the URL Shortener Service Interface methods
 */
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    private PropertyHolder properties;
    private ShortUrlDataService shortUrlDataService;
    private Utility utility;

    @Autowired
    public UrlShortenerServiceImpl(
            PropertyHolder properties,
            ShortUrlDataService shortUrlDataService,
            Utility utility)

    {
        this.properties = properties;
        this.shortUrlDataService = shortUrlDataService;
        this.utility = utility;
    }

    /**
     * It redirects to the long URL
     * @param shortUri the hashed uri key
     * @return
     */
    @Override
    public ResponseEntity redirectToLongUri(String shortUri) {
        try {
            ShortUrl shortUrlEntity = shortUrlDataService.searchUrl(shortUri);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(shortUrlEntity.getLongUrl());
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * It creates a short url and inserts in DB
     * @param createShortUrlRequest
     * @return
     */
    @Override
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        try {
            validate(createShortUrlRequest);
            String shortUrl = null;
            if(Objects.isNull(createShortUrlRequest.getShortUrl())) {
                shortUrl = createShortUrlFrom(createShortUrlRequest.getLongUrl());
            }
            else {
                validateShortUrl(createShortUrlRequest.getShortUrl());
            }
            ShortUrl shortUrlEntity = shortUrlDataService.insertNewUrl(shortUrl, createShortUrlRequest.getLongUrl(), Integer.parseInt(createShortUrlRequest.getTtlInDays()));
            return CreateShortUrlResponse.builder().
                    shortUrl(shortUrlEntity.getShortUrl()).
                    longUrl(shortUrlEntity.getLongUrl()).
                    createdAt(shortUrlEntity.getCreatedAt()).
                    expiryDate(shortUrlEntity.getExpiryDate()).
                    status(shortUrlEntity.getStatus()).
                    build();
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.SHORT_URL_ALREADY_EXISTS))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    private String createShortUrlFrom(String longUrl)
    {
        String shortUrl = utility.createShortUrl(longUrl, properties.getShortUrlLength());
        LOGGER.info("Created a new shortUrl for {} : {}", longUrl, shortUrl);
        while(doesUrlExistInDb(shortUrl))
        {
            shortUrl = utility.createShortUrl(longUrl, properties.getShortUrlLength());
            LOGGER.info("Created another short url {} for url {}, since the previous one exists in DB", shortUrl, longUrl);
        }
        return shortUrl;
    }


    private boolean doesUrlExistInDb(String shortUrl)
    {
        try {
            LOGGER.info("Checking if the url {} exists in DB or not", shortUrl);
            shortUrlDataService.searchUrl(shortUrl);
            LOGGER.info("The url {} exists in the DB", shortUrl);
            return true;
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                {
                    LOGGER.info("The url {} does not exist in the DB", shortUrl);
                    return false;
                }
            }
            else throw new RuntimeException(e);
        }
        return false;
    }

    private void validate(CreateShortUrlRequest createShortUrlRequest)
    {
        utility.validateUrl(createShortUrlRequest.getLongUrl());
        if(Objects.nonNull(createShortUrlRequest.getTtlInDays())) {
            utility.validateTtl(createShortUrlRequest.getTtlInDays(), properties.getMaxTtlInDaysAllowed());
        }
        else createShortUrlRequest.setTtlInDays(""+properties.getDefaultTtlInDays());
    }

    private void validateShortUrl(String shortUrl)
    {
        if(doesUrlExistInDb(shortUrl))
            throw new UrlShortenerException(ErrorCode.SHORT_URL_ALREADY_EXISTS, "Url " + shortUrl + " already exists");
    }


    /**
     * It retrieves shortUrl from the DB
     * @param shortUrl
     * @return
     */
    @Override
    public RetrieveShortUrlResponse retrieveShortUrl(String shortUrl) {
        try {
            ShortUrl shortUrlEntity = shortUrlDataService.searchUrl(shortUrl);
            return RetrieveShortUrlResponse.builder().
                    shortUrl(shortUrlEntity.getShortUrl()).
                    longUrl(shortUrlEntity.getLongUrl()).
                    createdAt(shortUrlEntity.getCreatedAt()).
                    updatedAt(shortUrlEntity.getUpdatedAt()).
                    expiryDate(shortUrlEntity.getExpiryDate()).
                    status(shortUrlEntity.getStatus()).
                    apiHitCount(shortUrlEntity.getApiHitCount()).
                    build();
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Override
    public List<RetrieveShortUrlResponse> retrieveAllUrls() {
        return null;
    }

    /**
     * Updates the ttl in DB
     * @param shortUrl
     * @param ttlInDays
     * @return
     */
    @Override
    public RetrieveShortUrlResponse updateTtl(String shortUrl, String ttlInDays) {
        try {
            utility.validateTtl(ttlInDays, properties.getMaxTtlInDaysAllowed());
            ShortUrl shortUrlEntity = shortUrlDataService.searchUrl(shortUrl);
            shortUrlEntity.setExpiryDate(utility.getExpiryDateFrom(Integer.parseInt(ttlInDays)));
            ShortUrl finalShortUrlEntity = shortUrlDataService.updateShortUrl(shortUrlEntity);
            return RetrieveShortUrlResponse.builder().
                    shortUrl(finalShortUrlEntity.getShortUrl()).
                    longUrl(finalShortUrlEntity.getLongUrl()).
                    createdAt(finalShortUrlEntity.getCreatedAt()).
                    expiryDate(finalShortUrlEntity.getExpiryDate()).
                    status(finalShortUrlEntity.getStatus()).
                    build();
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.INVALID_TTL_ERROR) || ((UrlShortenerException) e).getErrorCode().equals(ErrorCode.TTL_MAX_LIMIT_CROSSED))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Updates the shortUrl status in DB
     * @param shortUrl
     * @param status - ACTIVE/INACTIVE
     * @return
     */
    @Override
    public RetrieveShortUrlResponse updateStatus(String shortUrl, String status) {
        try {
            utility.validateStatus(status);
            ShortUrl shortUrlEntity = shortUrlDataService.searchUrl(shortUrl);
            shortUrlEntity.setStatus(status);
            ShortUrl finalShortUrlEntity = shortUrlDataService.updateShortUrl(shortUrlEntity);
            return RetrieveShortUrlResponse.builder().
                    shortUrl(finalShortUrlEntity.getShortUrl()).
                    longUrl(finalShortUrlEntity.getLongUrl()).
                    createdAt(finalShortUrlEntity.getCreatedAt()).
                    expiryDate(finalShortUrlEntity.getExpiryDate()).
                    status(finalShortUrlEntity.getStatus()).
                    build();
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.INVALID_STATUS))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * Deletes the short URL from DB
     * @param shortUrl
     * @return
     */
    @Override
    public DeleteUrlResponse deleteUrl(String shortUrl) {
        try {
            ShortUrl shortUrlEntity = shortUrlDataService.searchUrl(shortUrl);
            shortUrlDataService.deleteShortUrl(shortUrl);
            return DeleteUrlResponse.builder().
                    shortUrl(shortUrl).
                    longUrl(shortUrlEntity.getLongUrl()).
                    status("DELETED").
                    build();
        }
        catch (Exception e)
        {
            if(e instanceof UrlShortenerException)
            {
                if(((UrlShortenerException) e).getErrorCode().equals(ErrorCode.URL_NOT_FOUND_ERROR))
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
