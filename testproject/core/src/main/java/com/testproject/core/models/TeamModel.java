package com.testproject.core.models;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeamModel {

    @ChildResource(name = "teamMembers")
    private List<Resource> teamMembers;

    public List<TeamMember> getTeamMembers() {
        if (CollectionUtils.isEmpty(teamMembers)) {
            return Collections.emptyList();
        }
        return teamMembers.stream()
                .map(resource -> new TeamMember(
                        resource.getValueMap().get("name", String.class),
                        resource.getValueMap().get("role", String.class),
                        resource.getValueMap().get("image", String.class)
                ))
                .collect(Collectors.toList());
    }

    public static class TeamMember {
        private final String name;
        private final String role;
        private final String image;

        public TeamMember(String name, String role, String image) {
            this.name = name;
            this.role = role;
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public String getImage() {
            return image;
        }
    }
}
