package com.testproject.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.TestimonialSliderService;
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

@Component(service=TestimonialSliderService.class)
public class TestimonialSliderServiceImpl implements TestimonialSliderService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(TestimonialSliderServiceImpl.class);


    @Override
    public List<Map<String, String>> getTestimonialSliderList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "/graphql/execute.json/testproject/testimonial-slider";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("testimonialSliderList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawCustomerName = item.path("customerName").asText();
                        String customerName= HtmlUtils.stripOuterTag(rawCustomerName);
                        String rawRating = item.path("rating").asText();
                        String rating= HtmlUtils.stripOuterTag(rawRating);
                        String rawTestimonialText = item.path("testimonialText").path("html").asText();
                        String testimonialText=HtmlUtils.stripOuterTag(rawTestimonialText);
                        String rawCustomerImage = item.path("customerImage").path("_path").asText();
                        String customerImage= HtmlUtils.stripOuterTag(rawCustomerImage);
                        Map<String, String> map = new HashMap<>();
                        map.put("customerName", rawCustomerName);
                        map.put("rating", rawRating);
                        map.put("customerImage", rawCustomerImage);
                        map.put("testimonialText", rawTestimonialText);
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
