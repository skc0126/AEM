package com.testproject.core.services.impl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.core.services.ProductDetailsService;
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
@Component(service=ProductDetailsService.class)
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Reference
    private GraphqlDomainUtil graphqlDomain;
    private static final Logger LOG = LoggerFactory.getLogger(ProductDetailsServiceImpl.class);


    @Override
    public List<Map<String, String>> getProductDetailsList() {
        List<Map<String, String>> list = new ArrayList<>();
        String url = graphqlDomain.getGraphqlDomain() + "/graphql/execute.json/testproject/product-details";
        try (CloseableHttpClient httpClient = HttpClientUtil.createHttpClient(
                graphqlDomain.getUserName(), graphqlDomain.getPassword())) {

            HttpPost post = HttpClientUtil.createHttpPost(url);
            HttpEntity entity = httpClient.execute(post).getEntity();

            if (entity != null) {
                InputStream inputStream = entity.getContent();
                String jsonResponse = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(jsonResponse);
                JsonNode items = root.path("data").path("productDetailsList").path("items");

                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String rawProductName = item.path("productName").asText();
                        String productName= HtmlUtils.stripOuterTag(rawProductName);
                        String rawPrice = item.path("price").asText();
                        String price= HtmlUtils.stripOuterTag(rawPrice);
                        String rawFeatures = item.path("features").path("plaintext").asText();
                        String features= HtmlUtils.stripOuterTag(rawFeatures);
                        String rawDescription = item.path("description").path("html").asText();
                        String description=HtmlUtils.stripOuterTag(rawDescription);
                        String rawProductImage = item.path("productImage").path("_path").asText();
                        String productImage= HtmlUtils.stripOuterTag(rawProductImage);
                        Map<String, String> map = new HashMap<>();
                        map.put("productName", rawProductName);
                        map.put("price", rawPrice);
                        map.put("features", rawFeatures);
                        map.put("productImage", rawProductImage);
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
