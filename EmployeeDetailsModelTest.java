package com.adobe.aem.guides.wknd.core.models;

import static org.junit.jupiter.api.Assertions.*;

import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.adobe.xfa.ModelFactory;

public class EmployeeDetailsModelTest {

    private final AemContext context = new AemContext();
    private EmployeeDetailsModel employeeDetailsModel;

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(EmployeeDetailsModel.class);
        // Updated path based on your project structure
        context.load().json(
                "/com/adobe/aem/guides/wknd/core/models/components/employeeDetails.json",
                "/component");

        Resource resource = context.resourceResolver().getResource("/component/data");
        assertNotNull(resource, "Resource is null!");
        employeeDetailsModel = context.getService(ModelFactory.class).createModel(resource, EmployeeDetailsModel.class );
        assertNotNull(employeeDetailsModel, "Model adaptation failed!");
    }

    @Test
    void testGetName() {
        String expected = "John Doe";
        String actual = employeeDetailsModel.getName();
        assertEquals(expected, actual);
    }

     @Test
     void testGetAge(){
     String expected ="30";
     String actual = employeeDetailsModel.getAge();
     assertEquals(expected, actual);
     }

     @Test
     void testGetPhonenumber(){
     String expected ="1234567890";
     String actual = employeeDetailsModel.getPhonenumber();
     assertEquals(expected, actual);
     }

     @Test
     void testGetCompany(){
     String expected ="Adobe";
     String actual = employeeDetailsModel.getCompany();
     assertEquals(expected, actual);
     }

     @Test
     void testGetDesignation(){
     String expected ="Software Engineer";
     String actual = employeeDetailsModel.getDesignation();
     assertEquals(expected, actual);
     }
    //@Test
   // void testEmployeeDetails() {
    //    assertNotNull(employeeDetailsModel);
    //    assertEquals("John Doe", employeeDetailsModel.getName());
    //    assertEquals("30", employeeDetailsModel.getAge());
    //    assertEquals("1234567890", employeeDetailsModel.getPhonenumber());
    //    assertEquals("Adobe", employeeDetailsModel.getCompany());
     //   assertEquals("Software Engineer", employeeDetailsModel.getDesignation());
    //}
}