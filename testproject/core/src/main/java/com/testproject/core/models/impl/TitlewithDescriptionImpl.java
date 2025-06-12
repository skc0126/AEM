package com.testproject.core.models.impl;
import com.testproject.core.models.TitlewithDescriptionModel;
import com.testproject.core.services.TitlewithDescriptionService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= TitlewithDescriptionModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TitlewithDescriptionImpl implements TitlewithDescriptionModel {
    @OSGiService
    TitlewithDescriptionService service;

    @Override
    public List<Map<String, String>> getTitleDescriptionList() {
        return service.getTitleDescriptionList();
    }
}
