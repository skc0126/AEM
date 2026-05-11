package com.testproject.core.services;
import java.util.List;

public interface FactoryOSGiConfigModule {
    public String getConfigName();
    public int getConfigID();
    public String getConfigURL();
    public FactoryOSGiConfigModule get(int configID);
    public List<FactoryOSGiConfigModule> getAllConfigs();
} 