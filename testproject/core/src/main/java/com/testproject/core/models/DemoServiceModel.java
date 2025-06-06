package com.testproject.core.models;

import java.util.Iterator;

import com.day.cq.wcm.api.Page;

public interface DemoServiceModel {
    public Iterator<Page> getPagesList();
}
