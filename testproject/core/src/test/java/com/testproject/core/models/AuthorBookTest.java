package com.testproject.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.factory.ModelFactory;


import org.apache.sling.models.annotations.*;

import org.apache.sling.testing.mock.sling.MockSling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@ExtendWith(AemContextExtension.class)
public class AuthorBookTest {

    private final AemContext context = new AemContext();

   


    @BeforeEach
    public void setUp() {
        context.addModelsForClasses(AuthorBook.class);
    }

    @Test
    public void testGetFirstName() {}
        // Mock the ValueMap for the firstName field
        

    @Test
    public void testGetLastName() {
        // Mock the ValueMap for the lastName field
        
    }

    @Test
    public void testGetProfessor() {
        // Mock the resource for the professor field
        
    }

    @Test
    public void testGetPageTitle() {
        
    }

    @Test
    public void testGetRequestAttribute() {}
        

    @Test
    public void testGetHomePageName() {}
       

    @Test
    public void testGetModifiedBy() {
        // Mock the modifiedBy value
        
    }
}

