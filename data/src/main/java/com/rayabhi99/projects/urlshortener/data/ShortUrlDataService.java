package com.rayabhi99.projects.urlshortener.data;

import com.rayabhi99.projects.urlshortener.model.entity.ShortUrl;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Provides contract that can be used to perform IO on Short URL table in DB
 */
public interface ShortUrlDataService {
    /**
     * It searches the DB for shortUrl
     * @param shortUrl
     * @return
     * @throws Exception, if not found or any IO issues
     */
    public ShortUrl searchUrl(String shortUrl) throws Exception;
    /**
     * It searches the DB for all shortUrls registered under a user
     * @return
     * @throws Exception, for any IO issues
     */
    public List<ShortUrl> getAllUrls() throws Exception;
    /**
     * It inserts a new shortUrl to the DB
     * @param shortUrl
     * @return
     * @throws Exception, for any IO issues
     */
    public ShortUrl insertNewUrl(@NonNull String shortUrl, int ttlInDays) throws Exception;
    /**
     * It updates the shortUrl to the DB
     * @param shortUrl
     * @return
     * @throws Exception, for any IO issues
     */
    public ShortUrl updateShortUrl(ShortUrl shortUrl) throws Exception;

    /**
     * It deletes the shortUrl from the DB
     * @param shortUrl
     * @return
     * @throws Exception, for any IO issues
     */
    public void deleteShortUrl(String shortUrl) throws Exception;
}
