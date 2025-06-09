package com.testproject.core.config;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;



@ObjectClassDefinition(name = "Simple Test OSGi Factory Config", description = "This is a simple Factory OSGi Config")
public @interface TestFactoryOSGiConfig {

    @AttributeDefinition(name = "Service name", description = "Enter the name of the config", type = AttributeType.STRING)
    public String configName() default "Default Config Name";

    @AttributeDefinition(name = "Config ID", description = "Add config ID", type = AttributeType.INTEGER)
    int getConfigID() default 5;


    @AttributeDefinition(name = "Config URL", description = "Add config URL", type = AttributeType.STRING)
    String getConfigURL() default "www.xyz.com";



}
