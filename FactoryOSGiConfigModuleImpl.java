package com.testproject.core.services.impl;

import com.testproject.core.config.TestFactoryOSGiConfig;
import com.testproject.core.config.TestOSGiConfig;
import com.testproject.core.services.FactoryOSGiConfigModule;
import com.testproject.core.services.OSGiConfigModule;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Component(service = FactoryOSGiConfigModule.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = TestFactoryOSGiConfig.class, factory = true)
public class FactoryOSGiConfigModuleImpl implements FactoryOSGiConfigModule {

    

    private String configName;
    private int configID;
    private String configURL;
    private List<FactoryOSGiConfigModule> configModuleList;

    @Activate
    @Modified
    protected void activate(TestFactoryOSGiConfig testFactoryOSGiConfig) {
        configName = testFactoryOSGiConfig.configName();
        configID = testFactoryOSGiConfig.getConfigID();
        configURL = testFactoryOSGiConfig.getConfigURL();
        
    }

    @Reference(service= FactoryOSGiConfigModule.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void bindOSGiFactoryConfig(final FactoryOSGiConfigModule config){
        if (configModuleList == null){
            configModuleList = new ArrayList<>();
        }
        configModuleList.add(config);
    }
    public void unbindOSGiFactoryConfig(final FactoryOSGiConfigModule config){
    
        configModuleList.remove(config);
    }

    @Override
    public String getConfigName() {
       
        return configName;
    }

    @Override
    public int getConfigID() {
        return configID;
    }

    @Override
    public String getConfigURL() {
        return configURL;
    }

    @Override
    public FactoryOSGiConfigModule get(int configID) {
        for (FactoryOSGiConfigModule confFact : configModuleList) {
            if (configID==confFact.getConfigID())
                return confFact;
        }
        return null;
    }

    @Override
    public List<FactoryOSGiConfigModule> getAllConfigs() {
        return configModuleList;
    }


}
