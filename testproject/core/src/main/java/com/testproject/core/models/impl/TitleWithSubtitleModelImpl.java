package com.testproject.core.models.impl;

import com.testproject.core.models.TitleWithSubtitleModel;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
       adapters = TitleWithSubtitleModel.class,
       resourceType = "testproject/components/core-component/titlewithsubtitle",
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TitleWithSubtitleModelImpl implements TitleWithSubtitleModel {

    @ValueMapValue(name = "text")
    private String text;

    @ValueMapValue(name = "subtitle")
    private String subtitle;

    @Override
    public String getTitle() {
        return text;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }
}
