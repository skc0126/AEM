package com.testproject.core.models.impl;
import com.testproject.core.models.BlogTeaserListModel;
import com.testproject.core.services.BlogTeaserListService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= BlogTeaserListModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BlogTeaserListModelImpl implements BlogTeaserListModel {
    @OSGiService
    BlogTeaserListService service;

    @Override
    public List<Map<String, String>> getBlogTeaserList() {
        return service.getBlogTeaserList();
    }
}
