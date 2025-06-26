package com.testproject.core.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.BlogTeaserListService;
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

@Component(service=BlogTeaserListService.class)
public class BlogTeaserListServiceImpl implements BlogTeaserListService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(BlogTeaserListServiceImpl.class);


    @Override
    public List<Map<String, String>> getBlogTeaserList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "graphql/execute.json/testproject/blog-teaser-list";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("blogTeaserListList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawTitle = item.path("title").asText();
                        String title= HtmlUtils.stripOuterTag(rawTitle);
                        String rawAuthor = item.path("author").asText();
                        String author= HtmlUtils.stripOuterTag(rawAuthor);
                        String rawPublishDate = item.path("publishDate").asText();
                        String publishDate= HtmlUtils.stripOuterTag(rawPublishDate);
                        String rawSummary = item.path("summary").path("html").asText();
                        String summary=HtmlUtils.stripOuterTag(rawSummary);
                        String rawThumbnail = item.path("thumbnail").path("_path").asText();
                        String thumbnail= HtmlUtils.stripOuterTag(rawThumbnail);
                        Map<String, String> map = new HashMap<>();
                        map.put("title", rawTitle);
                        map.put("author", rawAuthor);
                        map.put("publishDate", rawPublishDate);
                        map.put("thumbnail", rawThumbnail);
                        map.put("summary", rawSummary);
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
