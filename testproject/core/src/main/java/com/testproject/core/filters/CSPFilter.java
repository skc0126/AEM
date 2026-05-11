package com.testproject.core.filters;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.featureflags.Features;

import org.osgi.service.component.annotations.Reference;

import java.security.MessageDigest;
import java.security.SecureRandom;

@Component(service = Filter.class, property = {
        "sling.filter.scope=REQUEST",
        "service.ranking:Integer=1000",
        "sling.filter.pattern=/content/testproject.*"
})
public class CSPFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(CSPFilter.class);

    @Reference
    private Features features; // Sling Feature Flag service

    private static final String NONCE_FEATURE_FLAG = "csp.nonce.enabled";
    // Define this flag in AEM (/system/console/slingfeatureflags)

    private static final String HEADER_CSP_SRC_NONCE = "X-CSP-SRC-NONCE";
    private static final String NONCE_ATTRIBUTE = "org.apache.sling.csp.nonce";
    private static final String NONCE_PREFIX = "nonce-";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("CSP Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;

        boolean nonceEnabled = features.isEnabled(NONCE_FEATURE_FLAG);
        log.debug("feature flag is executing for {}, nonceEnabled={}", slingRequest.getRequestURI(), nonceEnabled );

        if (nonceEnabled) {
            String nonce = generateShaNonce();

            slingRequest.setAttribute(NONCE_ATTRIBUTE, nonce);

            httpResponse.setHeader(HEADER_CSP_SRC_NONCE,NONCE_PREFIX + nonce);

            log.debug("Applied CSP header with nonce [{}]", nonce);
        } else {
            log.debug("Nonce feature flag disabled. CSP header not applied.");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.debug("CSP Filter destroyed");
    }

    private String generateShaNonce() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] randomBytes = new byte[32];
            random.nextBytes(randomBytes);

            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(randomBytes);

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            log.error("Error generating SHA nonce", e);
            return "";
        }
    }
}