package com.testproject.core.config;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;




@ObjectClassDefinition(name = "Simple Test OSGi Moduler Config", description = "This is a simple Moduler OSGi Config")
public @interface TestOSGiConfig {

    @AttributeDefinition(name = "Service name", description = "Enter the name of the service", type = AttributeType.STRING)
   String serviceName() default "Default Service Name";

    @AttributeDefinition(name = "Service ID", description = "Add service ID", type = AttributeType.INTEGER)
    int getServiceID() default 5;


    @AttributeDefinition(name = "Service URL", description = "Add service URL", type = AttributeType.STRING)
    String getServiceURL() default "www.xyz.com";



}
