package com.testproject.core.models.impl;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.testproject.core.models.DemoServiceModel;
import com.testproject.core.services.DemoService;

@Model(adaptables= SlingHttpServletRequest.class, adapters = DemoServiceModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DemoServiceModelImpl implements DemoServiceModel{

    //private static Logger log =LoggerFactory.getLogger(DemoServiceModelImpl.class);

    @OSGiService
    DemoService demoService;

   // @PostConstruct
    //public void init() {/*...*/
    //log.info("DemoServiceModelImpl init method called   .........."+ demoService.getPages());
    //}


    @Override
    public Iterator<Page> getPagesList() {
        // TODO Auto-generated method stub
       return demoService.getPages();
    }
    
    
}
