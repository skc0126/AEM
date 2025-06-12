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
public class HeroBannerTest {

    private final AemContext aemContext = new AemContext();

    HeroBanner heroBanner;

    @BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(ThirdComponent.class);
		aemContext.load().json("/components/herobanner/herobanner.json", "/component");
		Resource resource = aemContext.resourceResolver().getResource("/component/data");
        heroBanner = aemContext.getService(ModelFactory.class).createModel(resource,
        HeroBanner.class);
	}

    @Test
    void testGetMultifield() {
        String expectedImagePath = "/content/dam/testproject/language-master/City.jpeg";
        String actualImagePath = heroBanner.getMultifield().get(0).getImagePath();
        assertEquals(expectedImagePath,actualImagePath);

        String expectedImageAltText = "City";
        String actualImageAltText = heroBanner.getMultifield().get(0).getImageAltText();
        assertEquals(expectedImageAltText,actualImageAltText);

        String expectedDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,";
		String actualDescription = heroBanner.getMultifield().get(0).getDescription();
		assertEquals(expectedDescription,actualDescription);

        String expectedShortDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
		String actualShortDescription = heroBanner.getMultifield().get(0).getShortdescription();
		assertEquals(expectedShortDescription,actualShortDescription);
        
    }


    
}
