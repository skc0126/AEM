package com.testproject.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;


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

