package com.testproject.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "GraphQL Domain Configuration")
public @interface GraphqlDomainConfig {

    @AttributeDefinition(name = "GraphQL Domain URL", description = "Enter the environment domain", type = AttributeType.STRING)
    String graphqlDomain() default "http://localhost:4502";

    @AttributeDefinition(name = "Username", description = "Enter the environment username", type = AttributeType.STRING)
    String username() default "admin";

    @AttributeDefinition(name = "Password", description = "Enter the environment password", type = AttributeType.STRING)
    String password() default "admin";
}

