package com.testproject.core.services.impl;

import com.testproject.core.config.TestOSGiConfig;
import com.testproject.core.services.OSGiConfigModule;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;

@Component(service = OSGiConfigModule.class, immediate = true)
@Designate(ocd = TestOSGiConfig.class)
public class OSGiConfigModuleImpl implements OSGiConfigModule {

    

    private String serviceName;
    private int serviceID;
    private String serviceURL;

    @Activate
    protected void activate(TestOSGiConfig testOSGiConfig) {
       serviceName = testOSGiConfig.serviceName();
       serviceID = testOSGiConfig.getServiceID();
       serviceURL = testOSGiConfig.getServiceURL();
        
    }

    @Override
    public String getServiceName() {
       
        return serviceName;
    }

    @Override
    public int getServiceID() {
        return serviceID;
    }

    @Override
    public String getServiceURL() {
        return serviceURL;
    }


}
