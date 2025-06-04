/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.testproject.core.models;



import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.testproject.core.services.ContentService;



@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ComponentTest {

    private static final Logger LOG = LoggerFactory.getLogger(ComponentTest.class);

    @OSGiService
    ContentService contentService;

    @Inject
    @Via("jcr:title")
    private String jcrTitle;

    @PostConstruct
    protected void init() {
        LOG.info("ComponentTest initialized. jcr:title = {}", jcrTitle);
        //LOG.info("ComponentTest initialized. Resource Path = {}", Resource.getPath());
        LOG.info("ComponentTest initialized. jcr:title = {}", jcrTitle);
        LOG.trace("=============Log printed trace=========");
        LOG.info("=============Log printed info=========");
        LOG.debug("=============Log printed debug=========");
        LOG.warn("=============Log printed warn=========");
        LOG.error("=============Log printed error=========");
    }

    public String getTitle() {
        //return contentService.getContentProperty("/content/testproject/us/en/magazine/jcr:content", jcrTitle);
        return contentService.getContentProperty("/content/testproject/us/en/magazine/jcr:content", jcrTitle);
    }
}

