package com.testproject.core.utils;

import com.testproject.core.config.GraphqlDomainConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(service = GraphqlDomainUtil.class, immediate = true)
@Designate(ocd = GraphqlDomainConfig.class)
public class GraphqlDomainUtil {

    private String graphqlDomain;
    private String username;
    private String password;

    @Activate
    @Modified
    protected void activate(GraphqlDomainConfig config) {
        this.graphqlDomain = config.graphqlDomain();
        this.username = config.username();
        this.password = config.password();
    }

    public String getGraphqlDomain() {
        return graphqlDomain;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


















// package com.testproject.core.utils;

// import org.osgi.service.component.annotations.Component;

// @Component(service = GraphqlDomainUtil.class)
// public class GraphqlDomainUtil {

//     private static final String GRAPHQL_DOMAIN = "http://localhost:4502";
//     private static final String USERNAME = "admin";
//     private static final String PASSWORD = "admin";

//     public String getGraphqlDomain() {
//         return GRAPHQL_DOMAIN;
//     }

//     public String getUserName() {
//         return USERNAME;
//     }

//     public String getPassword() {
//         return PASSWORD;
//     }
// }