package com.testproject.core.models.impl;
import com.testproject.core.models.ProductDetailsModel;
import com.testproject.core.models.TeamMemberProfileModel;
import com.testproject.core.services.ProductDetailsService;
import com.testproject.core.services.TeamMemberProfileService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= TeamMemberProfileModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeamMemberProfileModelImpl implements TeamMemberProfileModel {
    @OSGiService
    TeamMemberProfileService service;

    @Override
    public List<Map<String, String>> getTeamMemberProfileList() {
        return service.getTeamMemberProfileList();
    }
}
