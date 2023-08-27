package com.rayabhi99.projects.urlshortener.data.impl;

import com.rayabhi99.projects.urlshortener.data.ShortUrlDataService;
import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the short url service interface
 */

@Service
public class ShortUrlDataServiceImpl implements ShortUrlDataService {
    @Override
    public ShortUrl searchUrl(String shortUrl) throws Exception {
        return null;
    }

    @Override
    public List<ShortUrl> getAllUrls() throws Exception {
        return null;
    }

    @Override
    public ShortUrl insertNewUrl(@NonNull String shortUrl, int ttlInDays) throws Exception {
        return null;
    }

    @Override
    public ShortUrl updateShortUrl(ShortUrl shortUrl) throws Exception {
        return null;
    }

    @Override
    public void deleteShortUrl(String shortUrl) throws Exception {

    }
}
