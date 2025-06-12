package com.testproject.core.utils;

import org.osgi.service.component.annotations.Component;

@Component(service = GraphqlDomainUtil.class)
public class GraphqlDomainUtil {

    private static final String GRAPHQL_DOMAIN = "http://localhost:4502";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public String getGraphqlDomain() {
        return GRAPHQL_DOMAIN;
    }

    public String getUserName() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }
}