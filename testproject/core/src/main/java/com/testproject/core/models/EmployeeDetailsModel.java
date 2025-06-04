package com.testproject.core.models;


import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EmployeeDetailsModel {


    @ValueMapValue
    private String name;

    @ValueMapValue
    private String age;

    @ValueMapValue
    private String number;

    @ValueMapValue
    private String company;

    @ValueMapValue
    private String designation;

    @ChildResource
    private List<EmployeeDetailsModelList> multifield;


    public String getNumber() {
        return number;
    }

    public List<EmployeeDetailsModelList> getMultifield() {
        return multifield;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getDesignation() {
        return designation;
    }

}
