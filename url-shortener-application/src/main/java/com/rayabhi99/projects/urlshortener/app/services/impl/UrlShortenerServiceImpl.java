package com.rayabhi99.projects.urlshortener.app.services.impl;

import com.rayabhi99.projects.urlshortener.app.services.UrlShortenerService;
import com.rayabhi99.projects.urlshortener.app.utility.Validator;
import com.rayabhi99.projects.urlshortener.data.ShortUrlDataService;
import com.rayabhi99.projects.urlshortener.model.PropertyHolder;
import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import com.rayabhi99.projects.urlshortener.model.request.CreateShortUrlRequest;
import com.rayabhi99.projects.urlshortener.model.response.CreateShortUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.DeleteUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.RetrieveShortUrlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private PropertyHolder properties;
    private ShortUrlDataService shortUrlDataService;
    private Validator validator;

    @Autowired
    public UrlShortenerServiceImpl(
            PropertyHolder properties,
            ShortUrlDataService shortUrlDataService,
            Validator validator)

    {
        this.properties = properties;
        this.shortUrlDataService = shortUrlDataService;
        this.validator = validator;
    }

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest createShortUrlRequest) {
        return null;
    }

    @Override
    public RetrieveShortUrlResponse retrieveShortUrl(String shortUrl) {
        return null;
    }

    @Override
    public List<RetrieveShortUrlResponse> retrieveAllUrls() {
        return null;
    }

    @Override
    public RetrieveShortUrlResponse updateTtl(String shortUrl, String ttlInDays) {
        return null;
    }

    @Override
    public RetrieveShortUrlResponse updateStatus(String shortUrl, String status) {
        return null;
    }

    @Override
    public DeleteUrlResponse deleteUrl(String shortUrl) {
        return null;
    }
}
