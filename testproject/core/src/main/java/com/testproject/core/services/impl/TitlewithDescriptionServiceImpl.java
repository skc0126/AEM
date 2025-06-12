package com.testproject.core.services.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.TitlewithDescriptionService;
import com.testproject.core.utils.GraphqlDomainUtil;
import com.testproject.core.utils.HtmlUtils;
import com.testproject.core.utils.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component(service=TitlewithDescriptionService.class)
public class TitlewithDescriptionServiceImpl implements TitlewithDescriptionService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(TitlewithDescriptionServiceImpl.class);


    @Override
    public List<Map<String, String>> getTitleDescriptionList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "/graphql/execute.json/testproject/title-with-description";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("titleWithDescriptionList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawTitle = item.path("title").path("html").asText();
                        String title= HtmlUtils.stripOuterTag(rawTitle);
                        String rawDescription = item.path("description").path("html").asText();
                        String description=HtmlUtils.stripOuterTag(rawDescription);
                        Map<String, String> map = new HashMap<>();
                        map.put("title", rawTitle);
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
