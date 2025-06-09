package com.testproject.core.models;

public interface OsgiServiceModel {
    public String serviceName();
    public int serviceCount();
    public boolean liveData();
    public String[] countries();
    public String runMode();
}
