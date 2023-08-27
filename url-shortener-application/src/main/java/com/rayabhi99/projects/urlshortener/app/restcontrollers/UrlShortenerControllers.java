package com.rayabhi99.projects.urlshortener.app.restcontrollers;

import com.rayabhi99.projects.urlshortener.app.api.UrlShortener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

@RestController
@Component
@CrossOrigin
@RequestMapping("/v1/urlShortener")
public class UrlShortenerControllers implements UrlShortener {
    @GetMapping(value = "/{shortUrl}")
    @Operation(method = "GET", description = "Redirects to the original long URL")
    @Override
    public @ResponseBody RedirectView redirectToLongUri(
            @PathVariable("shortUrl") String shortUrl
    )
    {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://www.google.com/");

        return redirectView;
    }
}
