package com.testproject.core.models.impl;

import com.testproject.core.models.OsgiServiceModel;
import com.testproject.core.models.OsgiServiceModuleModel;
import com.testproject.core.services.OSGiConfigModule;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables= SlingHttpServletRequest.class, adapters = OsgiServiceModuleModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OsgiServiceModuleModelImpl implements OsgiServiceModuleModel {

    @OSGiService
    private OSGiConfigModule osgiConfigModule;


    @Override
    public String serviceName() {
        return osgiConfigModule.getServiceName();
    }

    @Override
    public int serviceID() {
        return osgiConfigModule.getServiceID();
    }

    @Override
    public String serviceURL() {
        return osgiConfigModule.getServiceURL();
    }
    
}
