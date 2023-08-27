package com.rayabhi99.projects.urlshortener.app.api;

import org.springframework.web.servlet.view.RedirectView;

public interface UrlShortener {
    /**
     * It returns the redirection URL corresponding to teh long URL
     * @param shortUri
     * @return "redirect:<longUri>"
     *
     * @throws Exception, if shortUri is not valid with proper message
     */
    public RedirectView redirectToLongUri(String shortUri) throws Exception;

}
