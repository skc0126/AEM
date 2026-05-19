package com.testproject.core.services;

import org.apache.sling.api.resource.ResourceResolver;

public interface BusinessEnquiryService {
    boolean saveEnquiry(ResourceResolver resolver, String name, String email, String phone, String message);
}
