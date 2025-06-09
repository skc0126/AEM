package com.testproject.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

import com.testproject.core.services.OSGiConfig;

@Component(service = OSGiConfig.class, immediate = true)
@Designate(ocd = OSGiConfigImpl.ServiceConfig.class)
public class OSGiConfigImpl implements OSGiConfig {

    @ObjectClassDefinition(name = "Simple Test OSGi Config", description = "This is a simple Moduler OSGi Config")
public @interface ServiceConfig {

    @AttributeDefinition(name = "Service name", description = "Enter the name of the service", type = AttributeType.STRING)
    public String serviceName() default "Default Service Name";

    @AttributeDefinition(name = "Service count", description = "Add service count", type = AttributeType.INTEGER)
    int getServiceCount() default 5;

    @AttributeDefinition(name = "Live Data", description = "Check this id to get live data", type = AttributeType.BOOLEAN)
    boolean getLiveData() default false;

    @AttributeDefinition(name = "Countries", description = "Add countries locales for which you want to run this service", type = AttributeType.STRING)
    String[] getCountries() default { "de", "en", "fr" };

    @AttributeDefinition(name = "Run Modes", description = "Select Run Mode.", options = {
            @Option(label = "Author", value = "author"),
            @Option(label = "Publish", value = "publish"),
            @Option(label = "Both", value = "both")
    }, type = AttributeType.STRING)
    String getRunMode() default "both";

}
    

    private String serviceName;
    private int serviceCount;
    private boolean liveData;
    private String[] countries;
    private String runMode;

    @Activate
    protected void activate(ServiceConfig serviceConfig) {
       serviceName = serviceConfig.serviceName();
       serviceCount = serviceConfig.getServiceCount();
       liveData = serviceConfig.getLiveData();
       countries = serviceConfig.getCountries();
       runMode = serviceConfig.getRunMode();
        
    }

    @Override
    public String getServiceName() {
       
        return serviceName;
    }

    @Override
    public int getServiceCount() {
        return serviceCount;
    }

    @Override
    public boolean isLiveData() {
        
       return liveData;
    }

    @Override
    public String[] getCountries() {
        return countries;
    }

    @Override
    public String getRunMode() {
       return runMode;
    }

}
