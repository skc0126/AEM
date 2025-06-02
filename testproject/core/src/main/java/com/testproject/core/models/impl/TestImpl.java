package com.testproject.core.models.impl;

import com.testproject.core.models.Test;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
//import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;



@Model(adaptables = Resource.class, adapters = Test.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class TestImpl implements Test {

    @Inject
    @Default(values = "John")
    private String fname;

    @Inject
    private String lname;

    @Inject
    private boolean professor;

   

    @Override
    public String getFirstName() {
        // TODO Auto-generated method stub
        return fname;
    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return lname;
    }

    @Override
    public boolean getProfessor() {
        // TODO Auto-generated method stub
        return professor;
    }


}
