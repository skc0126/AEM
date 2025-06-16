package com.testproject.core.models.impl;
import com.testproject.core.models.EventDetailsModel;
import com.testproject.core.models.TitlewithDescriptionModel;
import com.testproject.core.services.EventDetailsService;
import com.testproject.core.services.TitlewithDescriptionService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= EventDetailsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EventDetailsModelImpl implements EventDetailsModel {
    @OSGiService
    EventDetailsService service;

    @Override
    public List<Map<String, String>> getEventDetailsList() {
        return service.getEventDetailsList();
    }
}
