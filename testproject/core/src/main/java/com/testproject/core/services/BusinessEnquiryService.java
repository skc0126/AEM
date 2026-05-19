package com.testproject.core.services;

public interface BusinessEnquiryService {
    boolean saveEnquiry(String name, String email, String phone, String message);
}
