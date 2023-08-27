package com.rayabhi99.projects.urlshortener.app.restcontrollers;

import com.rayabhi99.projects.urlshortener.app.restcontrollers.api.UrlShortenerControllerApi;
import com.rayabhi99.projects.urlshortener.app.services.UrlShortenerService;
import com.rayabhi99.projects.urlshortener.model.request.CreateShortUrlRequest;
import com.rayabhi99.projects.urlshortener.model.response.CreateShortUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.DeleteUrlResponse;
import com.rayabhi99.projects.urlshortener.model.response.RetrieveShortUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@CrossOrigin
@RequestMapping("/v1/urlShortener")
public class UrlShortenerControllers implements UrlShortenerControllerApi {
    private UrlShortenerService urlShortenerService;
    @Autowired
    public UrlShortenerControllers(
            UrlShortenerService urlShortenerService)

    {
        this.urlShortenerService = urlShortenerService;
    }
    @GetMapping(value = "/{shortUrl}")
    @Operation(method = "GET", description = "Redirects to the original long URL")
    @Override
    public @ResponseBody ResponseEntity redirectToLongUri(
           @Parameter(required = true) @PathVariable("shortUrl") String shortUrl
    )
    {
        return urlShortenerService.redirectToLongUri(shortUrl);
    }

    @PostMapping(value = "/createShortUrl")
    @Operation(method = "POST", description = "Creates short URL for a long URL")
    @Override
    public CreateShortUrlResponse createShortUrl(
            @RequestBody CreateShortUrlRequest createShortUrlRequest) {
        return urlShortenerService.createShortUrl(createShortUrlRequest);
    }

    @GetMapping(value = "/retreive/{shortUrl}")
    @Operation(method = "GET", description = "Fetches all metadata related to the short URL key")
    @Override
    public RetrieveShortUrlResponse retrieveShortUrl(
            @Parameter(required = true) @PathVariable("shortUrl") String shortUrl) {
        return urlShortenerService.retrieveShortUrl(shortUrl);
    }

    @GetMapping(value = "/retrieveAll")
    @Operation(method = "GET", description = "Fetches all metadata related to all the short urls, inserted by the user")
    @Override
    public List<RetrieveShortUrlResponse> retrieveAllUrls() {
        return urlShortenerService.retrieveAllUrls();
    }

    @PutMapping(value = "/updateTtl/{shortUrl}/{ttlInDays}")
    @Operation(method = "PUT", description = "Updates the TTL of a stored URL")
    @Override
    public RetrieveShortUrlResponse updateTtl(
            @PathVariable("shortUrl") String shortUrl,
            @PathVariable("ttlInDays") String ttlInDays) {
        return urlShortenerService.updateTtl(shortUrl, ttlInDays);
    }

    @PutMapping(value = "/updateStatus/{shortUrl}/{status}")
    @Operation(method = "PUT", description = "Updates the status of a stored URL")
    @Override
    public RetrieveShortUrlResponse updateStatus(
            @PathVariable("shortUrl") String shortUrl,
            @Parameter(name = "status", description = "Status of the short URL - ACTIVE/INACTIVE", required = true) @PathVariable("status")String status) throws Exception {
        return urlShortenerService.updateStatus(shortUrl, status);
    }

    @DeleteMapping(value = "/{shortUrl}")
    @Operation(method = "PUT", description = "Updates the status of a stored URL")
    @Override
    public DeleteUrlResponse deleteUrl(
            @PathVariable("shortUrl") String shortUrl) throws Exception {
        return urlShortenerService.deleteUrl(shortUrl);
    }

}
