package com.testproject.core.services.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.FAQAccordionService;
import com.testproject.core.utils.GraphqlDomainUtil;
import com.testproject.core.utils.HtmlUtils;
import com.testproject.core.utils.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
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
@Component(service=FAQAccordionService.class)
public class FAQAccordionServiceImpl implements FAQAccordionService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(FAQAccordionServiceImpl.class);


    @Override
    public List<Map<String, String>> getFAQAccordionList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "/graphql/execute.json/testproject/faq-accordion";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("faqAccordionList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawQuestion = item.path("question").asText();
                        String question= HtmlUtils.stripOuterTag(rawQuestion);
                        String rawAnswer = item.path("answer").path("html").asText();
                        String answer=HtmlUtils.stripOuterTag(rawAnswer);
                        Map<String, String> map = new HashMap<>();
                        map.put("question", rawQuestion);
                        map.put("answer", rawAnswer);
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
