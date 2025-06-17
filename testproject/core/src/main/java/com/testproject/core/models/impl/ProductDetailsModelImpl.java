package com.testproject.core.models.impl;
import com.testproject.core.models.ProductDetailsModel;
import com.testproject.core.services.ProductDetailsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import java.util.List;
import java.util.Map;

@Model(adaptables= SlingHttpServletRequest.class, adapters= ProductDetailsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailsModelImpl implements ProductDetailsModel {
    @OSGiService
    ProductDetailsService service;

    @Override
    public List<Map<String, String>> getProductDetailsList() {
        return service.getProductDetailsList();
    }
}
