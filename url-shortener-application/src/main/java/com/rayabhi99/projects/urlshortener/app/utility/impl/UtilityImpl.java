package com.rayabhi99.projects.urlshortener.app.utility.impl;

import com.rayabhi99.projects.urlshortener.app.utility.Utility;
import com.rayabhi99.projects.urlshortener.model.Status;
import com.rayabhi99.projects.urlshortener.model.error.ErrorCode;
import com.rayabhi99.projects.urlshortener.model.error.UrlShortenerException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UtilityImpl implements Utility {
    @Override
    public void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new UrlShortenerException(ErrorCode.URL_MALFORMED_ERROR, "Url is not valid", e);
        }
    }

    @Override
    public void validateTtl(String ttlInDays, int maxAllowedTtl) {
        int ttl;
        try {
            ttl = Integer.parseInt(ttlInDays);
        }
        catch (Exception e)
        {
            throw new UrlShortenerException(ErrorCode.INVALID_TTL_ERROR, "TTL is not valid", e);
        }
        if(ttl>maxAllowedTtl)
        {
            throw new UrlShortenerException(ErrorCode.TTL_MAX_LIMIT_CROSSED, "TTL max limit of ttl " + maxAllowedTtl + "d is not crossed");
        }
    }

    @Override
    public void validateStatus(String status) {
        for(Status status1: Status.values())
            if(status1.equals(status));
        throw new UrlShortenerException(ErrorCode.INVALID_STATUS, "Status is not valid and should be from : " + Arrays.stream(Status.values())
                .map(Enum::name)
                .collect(Collectors.joining(", ")));
    }

    @Override
    public String createShortUrl(String longUrl, int shorUrlLength) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(longUrl.getBytes());
        byte[] digest = md.digest();

        StringBuilder shortCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < shorUrlLength; i++) {
            int index = random.nextInt(digest.length);
            shortCode.append(Character.forDigit((digest[index] & 0xFF) % 62, 62));
        }

        return shortCode.toString();
    }

    @Override
    public String getExpiryDateFrom(int ttlInDays) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(ttlInDays);
        return futureDate.toString();
    }

    @Override
    public LocalDateTime convertToDateTimeFrom(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTime, formatter);
    }
}
