package com.rayabhi99.projects.urlshortener.app.utility.impl;

import com.rayabhi99.projects.urlshortener.app.utility.Validator;
import com.rayabhi99.projects.urlshortener.model.error.ErrorCode;
import com.rayabhi99.projects.urlshortener.model.error.UrlShortenerException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ValidatorImpl implements Validator {
    @Override
    public void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new UrlShortenerException(ErrorCode.URL_MALFORMED_ERROR, "Url is not valid", e);
        }
    }

    @Override
    public void validateTtl(String ttlInDays) {
        try {
            Integer.parseInt(ttlInDays);
        }
        catch (Exception e)
        {
            throw new UrlShortenerException(ErrorCode.INVALID_TTL_ERROR, "TTL is not valid", e);
        }
    }
}
