package com.rayabhi99.projects.urlshortener.app.restcontrollers.api;

import com.rayabhi99.projects.urlshortener.model.request.CreateShortUrlRequest;
import com.rayabhi99.projects.urlshortener.model.response.CreateShortUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.DeleteUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.RetrieveShortUrlResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UrlShortenerControllerApi {
    /**
     * It returns the redirection URL corresponding to teh long URL
     * @param shortUri the hashed uri key
     * @return redirect view
     *
     * @throws Exception, if shortUri is not valid with proper message
     */
    public ResponseEntity redirectToLongUri(String shortUri) throws Exception;


    /**
     * This method creates the short url corresponding to a long url and insert in the DB. It makes sure that duplicate short urls are not generated.
     * If same API is inserted again, it gives a new short URL.
     * If the ttl is marked as "NA", default ttl would be marked
     * @param createShortUrlRequest
     * @throws Exception, if some error occured while inserting the new url
     * @return
     */
    public CreateShortUrlResponse createShortUrl(CreateShortUrlRequest createShortUrlRequest) throws Exception;

    /**
     * This method returns the metadata related to the short url stored in System
     * @param shortUrl
     * @throws Exception, if the short Url does not exist or some unknown issues while retrieving the details
     * @return
     */
    public RetrieveShortUrlResponse retrieveShortUrl(String shortUrl) throws Exception;

    /**
     * The method retrieve all urls metadata, inserted by the user
     * @return
     */
    public List<RetrieveShortUrlResponse> retrieveAllUrls();

    /**
     * This method updates the ttl/ expiry date of the stored url
     * @param shortUrl
     * @param ttlInDays
     * @return
     * @throws Exception, if the short Url does not exist or some unknown issues while updating the ttl
     */
    public RetrieveShortUrlResponse updateTtl(String shortUrl, String ttlInDays) throws Exception;

    /**
     * This method updates the status of the url
     * @param shortUrl
     * @param status - ACTIVE/INACTIVE
     * @return
     * @throws Exception, if the short Url does not exist or some unknown issues while updating the status
     */
    public RetrieveShortUrlResponse updateStatus(String shortUrl, String status) throws Exception;

    /**
     * This method deletes the short url present in the system
     * @param shortUrl
     * @return
     * @throws Exception,  if the short Url does not exist or some unknown issues while deleting the url
     */
    public DeleteUrlResponse deleteUrl(String shortUrl) throws Exception;

}
