package com.testproject.core.services.impl;

import java.util.Iterator;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.testproject.core.services.DemoService;
import com.testproject.core.utils.ResolverUtil;


@Component(service = DemoService.class)
public class DemoServiceImpl implements DemoService {
    private final Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;



    @Override
    public Iterator<Page> getPages() {
        try {
            ResourceResolver resolver = ResolverUtil.newResolver(resourceResolverFactory);
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            Page page = pageManager.getPage("/content/testproject/language-master/en");
            Iterator<Page> pages = page.listChildren();
            return pages;

        } catch (LoginException e) {
            // TODO: handle exception
           log.info("Exception occured while getting pages" + e.getMessage());
        }
        return null;
    }
    
}
