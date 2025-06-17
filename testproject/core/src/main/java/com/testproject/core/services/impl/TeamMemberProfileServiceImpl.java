package com.testproject.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.TeamMemberProfileService;
import com.testproject.core.utils.GraphqlDomainUtil;
import com.testproject.core.utils.HtmlUtils;
import com.testproject.core.utils.HttpClientUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service=TeamMemberProfileService.class)
public class TeamMemberProfileServiceImpl implements TeamMemberProfileService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(TeamMemberProfileServiceImpl.class);


    @Override
    public List<Map<String, String>> getTeamMemberProfileList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "/graphql/execute.json/testproject/event-details";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("eventDetailsList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawEventTitle = item.path("eventTitle").asText();
                        String eventTitle= HtmlUtils.stripOuterTag(rawEventTitle);
                        String rawEventDate = item.path("eventDate").asText();
                        String eventDate= HtmlUtils.stripOuterTag(rawEventDate);
                        String rawLocation = item.path("location").asText();
                        String location= HtmlUtils.stripOuterTag(rawLocation);
                        String rawDescription = item.path("description").path("html").asText();
                        String description=HtmlUtils.stripOuterTag(rawDescription);
                        String rawEventImage = item.path("eventImage").path("_path").asText();
                        String eventImage= HtmlUtils.stripOuterTag(rawEventImage);
                        Map<String, String> map = new HashMap<>();
                        map.put("eventTitle", rawEventTitle);
                        map.put("eventDate", rawEventDate);
                        map.put("location", rawLocation);
                        map.put("eventImage", rawEventImage);
                        map.put("description", rawDescription);
                        list.add(map);
                    }
                }
            }
        }

    catch (Exception e) {
        e.printStackTrace();

    }
        return list;

    }
}
