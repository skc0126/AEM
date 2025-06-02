package com.testproject.core.models;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.search.QueryBuilder;
import com.day.cq.wcm.api.Page;

@Model(adaptables =  SlingHttpServletRequest.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorBook {

    //@Inject
    //@Via("resource")
    @ValueMapValue
    @Default(values = "Test")
    private String firstName;
    
    //@Inject
    //@Via("resource")
    @ValueMapValue
    @Default(values = "Test")
    private String lastName;

    @Inject
    @Via("resource")
    private boolean professor;

    @ScriptVariable
    Page currentPage;

    @SlingObject
    ResourceResolver resourceResolver;

    @Self
    SlingHttpServletRequest slingHttpServletRequest;

    @RequestAttribute(name = "rAttribute")
    private String reqAttribute;

    // @ResourcePath(path = "/content/testproject/us/en/Home")
    // @Via("resource")
    // Resource resource;

    @ResourcePath(path = "/content/testproject/us/en/home")
    @Via("resource")
    Resource resource;

    @OSGiService
    QueryBuilder queryBuilder;

    @Inject
    @Via("resource")
    @Named("jcr:lastModifiedBy")
    String modifiedBy;



    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public boolean getProfessor() {
        return professor;
    }

    public String getPageTitle() {
        return currentPage.getName();
    }

    public String getRequestAttribute() {
        return reqAttribute;
    }

   public String getHomePageName() {
      return  resource.getName();
   }

   public String getModifiedBy() {
    return  modifiedBy;
 }

   @PostConstruct
   protected void init() {
  
         System.out.println("ResourceResolver is not null");
      
   }


}
