package com.testproject.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.testproject.core.utils.MyServiceUser;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
           property = { "sling.servlet.paths=/bin/myserviceuser",
                       "sling.servlet.methods=GET" })
public class MyServlet extends SlingSafeMethodsServlet {

    @Reference
    private MyServiceUser myServiceUser;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
            throws ServletException, IOException {
        myServiceUser.fetchContent();
        response.getWriter().write("Service Executed!");
    }
}
