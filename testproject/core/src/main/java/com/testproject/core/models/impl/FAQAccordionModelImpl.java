package com.testproject.core.models.impl;
import com.testproject.core.models.FAQAccordionModel;
import com.testproject.core.services.FAQAccordionService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= FAQAccordionModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FAQAccordionModelImpl implements FAQAccordionModel {
    @OSGiService
    FAQAccordionService service;

    @Override
    public List<Map<String, String>> getFAQAccordionList() {
        return service.getFAQAccordionList();
    }
}
