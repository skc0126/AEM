package com.testproject.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroBannerList {
    
    @ValueMapValue
    private String imagePath;

    @ValueMapValue
    private String imageAltText;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String shortdescription;


    public String getImagePath() {
        return imagePath;
    }

    public String getImageAltText() {
        return imageAltText;
    }

    public String getDescription() {
        return description;
    }

    public String getShortdescription() {
        return shortdescription;
    }
    
}
