package com.testproject.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ThirdComponent {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String text;

    @ChildResource
    private List<ThirdComponentList> multifield;


    public List<ThirdComponentList> getMultifield() {
        return multifield;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
