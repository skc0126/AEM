package com.testproject.core.servlets;

import com.testproject.core.services.BusinessEnquiryService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(
    service = { Servlet.class },
    property = {
        "sling.servlet.paths=/bin/businessenquiry",
        "sling.servlet.methods=POST"
    }
)
public class BusinessEnquiryServlet extends SlingAllMethodsServlet {

    @Reference
    private BusinessEnquiryService enquiryService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");

        boolean success = enquiryService.saveEnquiry(name, email, phone, message);

        response.setContentType("application/json");
        if(success) {
            response.getWriter().write("{\"message\":\"Thank you for your enquiry!\"}");
        } else {
            response.getWriter().write("{\"message\":\"Failed to submit enquiry.\"}");
        }
    }
}
