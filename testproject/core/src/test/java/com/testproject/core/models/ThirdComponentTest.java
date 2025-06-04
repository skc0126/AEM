package com.testproject.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ThirdComponentTest {

    private final AemContext aemContext = new AemContext();

    ThirdComponent thirdComponent;

    @BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(ThirdComponent.class);
		aemContext.load().json("/components/thirdcomponent/ThirdComponent.json", "/component");
		Resource resource = aemContext.resourceResolver().getResource("/component/data");
		thirdComponent = aemContext.getService(ModelFactory.class).createModel(resource,
        ThirdComponent.class);
	}

    @Test
    void testGetMultifield() {
        String expectedEyebrow = "sdfdf";
		String actualEyebrow = thirdComponent.getMultifield().get(0).getEyebrow();
		assertEquals(expectedEyebrow,actualEyebrow);

        String expectedHeader = "dvvd";
		String actualHeader = thirdComponent.getMultifield().get(0).getHeader();
		assertEquals(expectedHeader,actualHeader);
        
    }

    @Test
    void testGetText() {
        String expected = "sfgs";
		String actual = thirdComponent.getText();
		assertEquals(expected,actual);
    }

    @Test
    void testGetTitle() {
        String expected = "feger";
		String actual = thirdComponent.getTitle();
		assertEquals(expected,actual);
    }

    
}
