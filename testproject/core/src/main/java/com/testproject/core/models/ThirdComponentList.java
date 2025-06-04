package com.testproject.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ThirdComponentList {
    
    @ValueMapValue
    private String eyebrow;

    @ValueMapValue
    private String header;


    public String getEyebrow() {
        return eyebrow;
    }

    public String getHeader() {
        return header;
    }
    
}
