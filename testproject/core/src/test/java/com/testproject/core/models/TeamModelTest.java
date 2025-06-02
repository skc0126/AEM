package com.testproject.core.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() throws Exception {
        context.addModelsForClasses(TeamModel.class);

        // Load JSON safely via Jackson to avoid SAX error
        InputStream jsonStream = getClass().getResourceAsStream("/team-component.json");
        assertNotNull(jsonStream, "JSON resource not found");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonStream);

        // Manually build the content structure
        JsonNode team = root.get("team");
        context.create().resource("/content/component/team");

        if (team.has("teamMembers")) {
            JsonNode members = team.get("teamMembers");
            context.create().resource("/content/component/team/teamMembers");

            members.fieldNames().forEachRemaining(field -> {
                JsonNode member = members.get(field);
                context.create().resource("/content/component/team/teamMembers/" + field,
                    "name", member.get("name").asText(),
                    "role", member.get("role").asText(),
                    "image", member.get("image").asText()
                );
            });
        }
    }

    @Test
    void testTeamMembers() {
        Resource resource = context.resourceResolver().getResource("/content/component/team");
        assertNotNull(resource);

        TeamModel model = resource.adaptTo(TeamModel.class);
        assertNotNull(model);

        List<TeamModel.TeamMember> members = model.getTeamMembers();
        assertNotNull(members);
        assertEquals(2, members.size());

        TeamModel.TeamMember first = members.get(0);
        assertEquals("Alice", first.getName());
        assertEquals("Developer", first.getRole());
        assertEquals("/content/dam/team/alice.jpg", first.getImage());

        TeamModel.TeamMember second = members.get(1);
        assertEquals("Bob", second.getName());
        assertEquals("Designer", second.getRole());
        assertEquals("/content/dam/team/bob.jpg", second.getImage());
    }
}
