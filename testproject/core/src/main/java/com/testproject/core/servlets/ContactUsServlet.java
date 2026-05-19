package com.testproject.core.servlets;

import com.testproject.core.services.ContactUsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(
    service = { Servlet.class },
    property = {
        "sling.servlet.paths=/bin/contactus",
        "sling.servlet.methods=POST"
    }
)
public class ContactUsServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ContactUsServlet.class);

    @Reference
    private ContactUsService contactUsService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            // Get form parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");

            // Trim values
            firstName = firstName != null ? firstName.trim() : "";
            lastName = lastName != null ? lastName.trim() : "";
            email = email != null ? email.trim() : "";
            phone = phone != null ? phone.trim() : "";
            subject = subject != null ? subject.trim() : "";
            message = message != null ? message.trim() : "";

            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write("{\"success\":false,\"message\":\"All fields are required.\"}");
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                response.setContentType("application/json");
                response.setStatus(400);
                response.getWriter().write("{\"success\":false,\"message\":\"Invalid email format.\"}");
                return;
            }

            // Check if service is available
            if (contactUsService == null) {
                LOG.error("ContactUsService is not available");
                response.setContentType("application/json");
                response.setStatus(500);
                response.getWriter().write("{\"success\":false,\"message\":\"Service not available. Please try again later.\"}");
                return;
            }

            // Save contact submission using request's resource resolver
            boolean success = contactUsService.saveContactUs(
                request.getResourceResolver(),
                firstName,
                lastName,
                email,
                phone,
                subject,
                message
            );

            response.setContentType("application/json");
            if (success) {
                response.setStatus(200);
                response.getWriter().write("{\"success\":true,\"message\":\"Thank you! We will get back to you soon.\"}");
            } else {
                response.setStatus(500);
                response.getWriter().write("{\"success\":false,\"message\":\"Failed to submit form. Please try again.\"}");
            }
        } catch (Exception e) {
            LOG.error("Error processing contact form submission", e);
            response.setContentType("application/json");
            response.setStatus(500);
            response.getWriter().write("{\"success\":false,\"message\":\"An error occurred. Please try again.\"}");
        }
    }

    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
