package com.testproject.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.testproject.core.models.OsgiServiceModel;
import com.testproject.core.services.OSGiConfig;

@Model(adaptables= SlingHttpServletRequest.class, adapters = OsgiServiceModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OsgiServiceModelImpl implements OsgiServiceModel {

    @OSGiService
    private OSGiConfig osgiConfig;


    @Override
    public String serviceName() {
        return osgiConfig.getServiceName();
    }

    @Override
    public int serviceCount() {
        return osgiConfig.getServiceCount();
    }

    @Override
    public boolean liveData() {
        return osgiConfig.isLiveData();
    }

    @Override
    public String[] countries() {
        return osgiConfig.getCountries();
    }

    @Override
    public String runMode() {
        return osgiConfig.getRunMode();
    }
    
}
