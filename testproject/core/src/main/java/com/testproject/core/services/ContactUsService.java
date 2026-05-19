package com.testproject.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface ContactUsService {
    /**
     * Save contact us form submission
     * @param resolver ResourceResolver for accessing JCR
     * @param firstName First name of the contact
     * @param lastName Last name of the contact
     * @param email Email address
     * @param phone Phone number
     * @param subject Subject of the message
     * @param message Message content
     * @return true if saved successfully, false otherwise
     */
    boolean saveContactUs(ResourceResolver resolver, String firstName, String lastName, String email, String phone, String subject, String message);
}
