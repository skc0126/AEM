package com.testproject.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.testproject.core.services.ContentService;


import org.osgi.service.component.annotations.Reference;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ContentService.class)
public class ContentServiceImpl implements ContentService {

    private static final Logger LOG = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Reference
    private ResourceResolver resourceResolver;

    @Override
    public String getContentProperty(String path, String propertyName) {
        try {
            Resource resource = resourceResolver.getResource(path);
            if (resource != null) {
                Node node = resource.adaptTo(Node.class);
                if (node != null && node.hasProperty(propertyName)) {
                    return node.getProperty(propertyName).getString();
                }
            }
        } catch (RepositoryException e) {
            LOG.error("Error fetching property {} from {}", propertyName, path, e);
        }
        return null;
    }
}
