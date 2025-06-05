package com.testproject.core.utils;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.Resource;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = MyServiceUser.class)
public class MyServiceUser {

    @Reference
    private ResourceResolverFactory resolverFactory;

    public void fetchContent() {
        try {
            // Get Resource Resolver using System User
            Map<String, Object> params = new HashMap<>();
            params.put(ResourceResolverFactory.SUBSERVICE, "testserviceuser");
            ResourceResolver resolver = resolverFactory.getServiceResourceResolver(params);

            // Access JCR Content
            Resource resource = resolver.getResource("/content/testproject/in/en/home");
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                System.out.println("Node Name: " + node.getName());
            }

            // Close Resolver
            resolver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}